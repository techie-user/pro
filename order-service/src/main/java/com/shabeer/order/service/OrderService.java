package com.shabeer.order.service;

import com.shabeer.common.kafka.TopicNames;
import com.shabeer.common.kafka.dto.OrderCreatedEvent;
import com.shabeer.order.domain.OrderEntity;
import com.shabeer.order.domain.OrderItemEmbeddable;
import com.shabeer.order.domain.OrderRepository;
import com.shabeer.order.web.OrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderService(OrderRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<String> createOrder(OrderDto dto) {
        return Mono.fromCallable(() -> {
            String id = UUID.randomUUID().toString();
            OrderEntity entity = new OrderEntity();
            entity.setId(id);
            entity.setCustomerId(dto.customerId());
            entity.setCreatedAt(Instant.now());
            entity.setStatus("CREATED");
            entity.setItems(dto.items().stream().map(li -> {
                OrderItemEmbeddable emb = new OrderItemEmbeddable();
                emb.setSku(li.sku());
                emb.setQuantity(li.quantity());
                return emb;
            }).collect(Collectors.toList()));
            repository.save(entity);

            OrderCreatedEvent event = new OrderCreatedEvent(
                    id,
                    dto.customerId(),
                    dto.items().stream()
                            .map(li -> new OrderCreatedEvent.LineItem(li.sku(), li.quantity()))
                            .toList()
            );
            kafkaTemplate.send(TopicNames.ORDER_CREATED, id, event);
            return id;
        });
    }
}
