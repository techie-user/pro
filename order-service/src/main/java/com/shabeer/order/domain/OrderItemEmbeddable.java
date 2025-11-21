package com.shabeer.order.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItemEmbeddable {

    private String sku;
    private int quantity;

    public String getSku() { return sku; }

    public void setSku(String sku) { this.sku = sku; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
