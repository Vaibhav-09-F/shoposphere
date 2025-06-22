package com.ecom.controller;

import com.ecom.dto.order.OrderOverviewResponse;
import com.ecom.dto.order.OrderResponse;          // NEW import
import com.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public OrderResponse placeOrder(@RequestParam Long userId) {
        return orderService.placeOrder(userId);
    }

    @GetMapping("/my")
    public List<OrderOverviewResponse> getMyOrders(@RequestParam Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/admin/all")
    public List<OrderOverviewResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

}
