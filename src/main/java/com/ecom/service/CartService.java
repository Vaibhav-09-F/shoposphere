package com.ecom.service;

import com.ecom.model.CartItem;

import java.util.List;

public interface CartService {
    CartItem addToCart(Long userId, Long productId, int quantity);
    List<CartItem> getCartItems(Long userId);
    void removeFromCart(Long userId, Long productId);
}
