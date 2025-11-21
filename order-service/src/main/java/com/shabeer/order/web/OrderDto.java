package com.shabeer.order.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderDto(
        @NotBlank String customerId,
        @NotEmpty List<LineItem> items
) {
    public record LineItem(
            @NotBlank String sku,
            @Positive int quantity
    ) {}
}
