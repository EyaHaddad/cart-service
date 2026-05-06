package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.model.CartItem;
import com.ecommerce.cartservice.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Add a product to the cart.
     * If the product already exists, increment the quantity.
     */
    public CartItem addToCart(CartItem cartItem) {
        Optional<CartItem> existingItem = cartItemRepository.findByProductId(cartItem.getProductId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            return cartItemRepository.save(item);
        }

        return cartItemRepository.save(cartItem);
    }

    /**
     * Get all items in the cart
     */
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    /**
     * Remove a specific product from the cart
     */
    @Transactional
    public void removeFromCart(Long productId) {
        cartItemRepository.deleteByProductId(productId);
    }

    /**
     * Clear the entire cart
     */
    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}