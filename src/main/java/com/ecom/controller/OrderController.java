package com.ecom.controller;

import com.ecom.dto.order.OrderResponse;          // NEW import
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public OrderResponse placeOrder(@RequestParam Long userId) {
        return orderService.placeOrder(userId);   // now returns DTO
    }

    @GetMapping("/{userId}")
    public List<OrderResponse> getOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);  // returns list of DTOs
    }
}
