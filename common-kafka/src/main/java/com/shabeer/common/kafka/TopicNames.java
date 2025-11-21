package com.shabeer.common.kafka;

public final class TopicNames {
    private TopicNames() {}

    public static final String ORDER_CREATED = "order.created";
    public static final String ORDER_VALIDATED = "order.validated";
    public static final String INVENTORY_RESERVED = "inventory.reserved";
    public static final String INVENTORY_FAILED = "inventory.failed";
    public static final String ROUTE_CREATED = "route.created";
    public static final String ORDER_FULFILLED = "order.fulfilled";
}
