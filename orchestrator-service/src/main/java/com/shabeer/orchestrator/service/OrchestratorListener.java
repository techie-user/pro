package com.shabeer.orchestrator.service;

import com.shabeer.common.kafka.TopicNames;
import com.shabeer.common.kafka.dto.RouteCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrchestratorListener {

    private static final Logger log = LoggerFactory.getLogger(OrchestratorListener.class);

    @KafkaListener(topics = TopicNames.ROUTE_CREATED, groupId = "orchestrator-service")
    public void handleRouteCreated(RouteCreatedEvent event) {
        // in a real saga we would update order status, notify customer, etc.
        log.info("Order {} is ready for delivery via route {} in {} minutes",
                event.orderId(), event.routeId(), event.estimatedMinutes());
    }
}
