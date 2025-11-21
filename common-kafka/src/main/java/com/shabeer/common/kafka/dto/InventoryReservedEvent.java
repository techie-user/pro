package com.shabeer.common.kafka.dto;

public record InventoryReservedEvent(
        String orderId,
        boolean success,
        String reason
) {}
