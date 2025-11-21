package com.shabeer.order.web;

import com.shabeer.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<CreateOrderResponse> createOrder(@Valid @RequestBody Mono<OrderDto> dtoMono) {
        return dtoMono
                .flatMap(service::createOrder)
                .map(CreateOrderResponse::new);
    }

    public record CreateOrderResponse(String orderId) {}
}
