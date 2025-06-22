package com.ecom.dto.cart;

public class CartItemResponse {
    private Long   id;
    private Long   productId;
    private String productName;
    private int    quantity;
    private Double productPrice;

    public CartItemResponse() {}

    public CartItemResponse(Long id, Long productId, String productName, int quantity, Double productPrice) {
        this.id           = id;
        this.productId    = productId;
        this.productName  = productName;
        this.quantity     = quantity;
        this.productPrice = productPrice;
    }

    // getters & setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
