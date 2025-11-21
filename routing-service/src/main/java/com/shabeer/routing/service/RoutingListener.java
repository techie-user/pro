package com.shabeer.routing.service;

import com.shabeer.common.kafka.TopicNames;
import com.shabeer.common.kafka.dto.InventoryReservedEvent;
import com.shabeer.common.kafka.dto.RouteCreatedEvent;
import com.shabeer.routing.domain.RoutePlan;
import com.shabeer.routing.domain.RoutePlanRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RoutingListener {

    private final RoutePlanRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RoutingListener(RoutePlanRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = TopicNames.INVENTORY_RESERVED, groupId = "routing-service")
    public void handleInventoryReserved(InventoryReservedEvent event) {
        if (!event.success()) {
            return;
        }
        RoutePlan plan = new RoutePlan();
        plan.setId(UUID.randomUUID().toString());
        plan.setOrderId(event.orderId());
        int eta = ThreadLocalRandom.current().nextInt(15, 60);
        plan.setEstimatedMinutes(eta);
        repository.save(plan);

        RouteCreatedEvent routeEvent = new RouteCreatedEvent(event.orderId(), plan.getId(), eta);
        kafkaTemplate.send(TopicNames.ROUTE_CREATED, event.orderId(), routeEvent);
    }
}
