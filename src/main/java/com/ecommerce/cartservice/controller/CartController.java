package com.ecommerce.cartservice.controller;

import com.ecommerce.cartservice.dto.AddToCartRequest;
import com.ecommerce.cartservice.model.CartItem;
import com.ecommerce.cartservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * POST /api/cart
     * Add a product to the cart
     */
    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody AddToCartRequest request) {
        CartItem cartItem = new CartItem(
                request.getProductId(),
                request.getProductName(),
                request.getProductPrice(),
                request.getQuantity()
        );
        CartItem savedItem = cartService.addToCart(cartItem);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    /**
     * GET /api/cart
     * Get all items in the cart
     */
    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems() {
        List<CartItem> items = cartService.getCartItems();
        return ResponseEntity.ok(items);
    }

    /**
     * DELETE /api/cart/{productId}
     * Remove a specific product from the cart
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return ResponseEntity.ok("Product " + productId + " removed from cart");
    }

    /**
     * DELETE /api/cart
     * Clear the entire cart
     */
    @DeleteMapping
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Cart cleared successfully");
    }
}