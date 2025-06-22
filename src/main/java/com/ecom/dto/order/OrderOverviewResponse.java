// OrderOverviewResponse.java
package com.ecom.dto.order;

import java.util.List;

public class OrderOverviewResponse {
    private Long   orderId;
    private String status;
    private String orderDate;
    private Double totalAmount;
    private String userEmail;
    private List<OrderItemResponse> items;

    public OrderOverviewResponse(Long id, String name, String string, Double totalAmount, String email, List<OrderItemResponse> items) {
    }

    public OrderOverviewResponse() {

    }

    // Getters and setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
