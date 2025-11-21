package com.shabeer.common.kafka.dto;

public record RouteCreatedEvent(
        String orderId,
        String routeId,
        int estimatedMinutes
) {}
