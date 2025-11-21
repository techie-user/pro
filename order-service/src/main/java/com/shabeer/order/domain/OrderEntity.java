package com.shabeer.order.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private String id;

    private String customerId;

    private Instant createdAt;

    private String status;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItemEmbeddable> items;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }

    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public List<OrderItemEmbeddable> getItems() { return items; }

    public void setItems(List<OrderItemEmbeddable> items) { this.items = items; }
}
