package com.shabeer.inventory.service;

import com.shabeer.common.kafka.TopicNames;
import com.shabeer.common.kafka.dto.InventoryReservedEvent;
import com.shabeer.common.kafka.dto.OrderCreatedEvent;
import com.shabeer.inventory.domain.InventoryItem;
import com.shabeer.inventory.domain.InventoryRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryListener {

    private final InventoryRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryListener(InventoryRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = TopicNames.ORDER_CREATED, groupId = "inventory-service")
    @Transactional
    public void handleOrderCreated(OrderCreatedEvent event) {
        boolean allAvailable = event.items().stream().allMatch(li -> {
            InventoryItem item = repository.findById(li.sku()).orElse(null);
            return item != null && item.getQuantity() >= li.quantity();
        });

        if (allAvailable) {
            event.items().forEach(li -> {
                InventoryItem item = repository.findById(li.sku()).orElseThrow();
                item.setQuantity(item.getQuantity() - li.quantity());
                repository.save(item);
            });
            kafkaTemplate.send(TopicNames.INVENTORY_RESERVED,
                    event.orderId(),
                    new InventoryReservedEvent(event.orderId(), true, "OK"));
        } else {
            kafkaTemplate.send(TopicNames.INVENTORY_RESERVED,
                    event.orderId(),
                    new InventoryReservedEvent(event.orderId(), false, "INSUFFICIENT_STOCK"));
        }
    }
}
