package com.test.ecommerce.controller;

import com.test.ecommerce.dto.*;
import com.test.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Integer orderId,
            @RequestBody OrderRequest request) {

        return ResponseEntity.ok(orderService.updateOrder(orderId, request));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItemResponse> addOrderItem(
            @PathVariable Integer orderId,
            @RequestBody OrderItemRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.addOrderItem(orderId, request));
    }

    @PutMapping("/{orderId}/items/{orderItemSeqId}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer orderItemSeqId,
            @RequestBody OrderItemRequest request) {

        return ResponseEntity.ok(
                orderService.updateOrderItem(orderId, orderItemSeqId, request));
    }

    @DeleteMapping("/{orderId}/items/{orderItemSeqId}")
    public ResponseEntity<Void> deleteOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer orderItemSeqId) {

        orderService.deleteOrderItem(orderId, orderItemSeqId);
        return ResponseEntity.noContent().build();
    }
}
