package com.ecom.service.impl;

import com.ecom.dto.order.OrderItemResponse;
import com.ecom.dto.order.OrderResponse;
import com.ecom.model.*;
import com.ecom.repository.*;
import com.ecom.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private UserRepository userRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;

    // ---------- PLACE ORDER ----------
    @Override
    @Transactional
    public OrderResponse placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(
                    cartItem.getProduct().getPrice() * cartItem.getQuantity());
            orderItem.setOrder(order);

            totalAmount += orderItem.getPrice();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order saved = orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAll(cartItems);   // clear cart

        return toResponse(saved);
    }

    // ---------- GET ORDERS BY USER ----------
    @Override
    @Transactional
    public List<OrderResponse> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> responses = new ArrayList<>();
        for (Order o : orders) {
            responses.add(toResponse(o));
        }
        return responses;
    }

    // ---------- ENTITY → DTO MAPPER ----------
    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemDTOs = order.getItems().stream()
                .map(oi -> new OrderItemResponse(
                        oi.getProduct().getId(),
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getPrice()))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                itemDTOs);
    }
}
