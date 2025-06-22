package com.ecom.service;

import com.ecom.dto.cart.CartItemResponse;
import com.ecom.model.CartItem;

import java.util.List;

public interface CartService {
    List<CartItemResponse> getCartItems(Long userId);
    CartItemResponse addToCart(Long userId, Long productId, int quantity);
    void removeFromCart(Long userId, Long productId);
}
