package com.ecom.service;

import com.ecom.dto.order.OrderOverviewResponse;
import com.ecom.dto.order.OrderResponse;
import com.ecom.model.Order;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(Long userId);
    List<OrderResponse> getOrdersByUser(Long userId);

    List<OrderOverviewResponse> getUserOrders(Long userId);
    List<OrderOverviewResponse> getAllOrders(); // Admin-only

}

