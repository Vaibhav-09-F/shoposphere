package com.ecom.controller;

import com.ecom.dto.cart.CartItemResponse;
import com.ecom.model.CartItem;
import com.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// src/main/java/com/ecom/controller/CartController.java
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartItemResponse addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @GetMapping("/{userId}")
    public List<CartItemResponse> getCartItems(@PathVariable Long userId) {
        return cartService.getCartItems(userId);
    }

    @DeleteMapping("/remove")
    public void removeFromCart(
            @RequestParam Long userId,
            @RequestParam Long productId
    ) {
        cartService.removeFromCart(userId, productId);
    }
}