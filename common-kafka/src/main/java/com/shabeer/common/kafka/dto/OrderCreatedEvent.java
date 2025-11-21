package com.shabeer.common.kafka.dto;

import java.util.List;

public record OrderCreatedEvent(
        String orderId,
        String customerId,
        List<LineItem> items
) {
    public record LineItem(String sku, int quantity) {}
}
