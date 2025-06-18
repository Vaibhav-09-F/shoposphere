package com.ecom.service;

import com.ecom.dto.order.OrderResponse;
import com.ecom.model.Order;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(Long userId);
    List<OrderResponse> getOrdersByUser(Long userId);
}

