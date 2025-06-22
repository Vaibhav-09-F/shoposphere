package com.ecom.service.impl;

import com.ecom.dto.order.OrderItemResponse;
import com.ecom.dto.order.OrderOverviewResponse;
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
import java.util.stream.Collectors;

@Service
@Transactional
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
        orderItemRepository.saveAll(order.getItems());

        cartItemRepository.deleteAll(cartItems);   // clear cart

        return toResponse(saved);
    }

    // ---------- GET ORDERS BY USER ----------
    @Override
    @Transactional
    public List<OrderOverviewResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::toOverviewDto)
                .toList();
    }

    @Override
    @Transactional
    public List<OrderOverviewResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toOverviewDto)
                .toList();
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

    @Override
    public List<OrderOverviewResponse> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::mapToOverviewResponse).collect(Collectors.toList());
    }

    private OrderOverviewResponse mapToOverviewResponse(Order order) {
        OrderOverviewResponse response = new OrderOverviewResponse();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setOrderDate(order.getOrderDate().toString());
        response.setTotalAmount(order.getTotalAmount());
        response.setUserEmail(order.getUser().getEmail());

        List<OrderItemResponse> itemResponses = order.getItems().stream().map(item -> {
            OrderItemResponse itemResp = new OrderItemResponse();
            itemResp.setProductName(item.getProduct().getName());
            itemResp.setQuantity(item.getQuantity());
            itemResp.setPrice(item.getPrice());
            return itemResp;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }

    private OrderOverviewResponse toOverviewDto(Order order) {
        OrderOverviewResponse dto = new OrderOverviewResponse();
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus().name());
        dto.setOrderDate(order.getOrderDate().toString());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setUserEmail(order.getUser().getEmail());

        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> {
                    OrderItemResponse ir = new OrderItemResponse();
                    ir.setProductId(item.getProduct().getId());        // ← SET productId
                    ir.setProductName(item.getProduct().getName());
                    ir.setQuantity(item.getQuantity());
                    ir.setPrice(item.getPrice());
                    return ir;
                })
                .toList();

        dto.setItems(items);
        return dto;
    }

}
