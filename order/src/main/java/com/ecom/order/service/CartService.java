package com.ecom.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecom.order.clients.ProductServiceClient;
import com.ecom.order.clients.UserServiceClient;
import com.ecom.order.dto.CartItemRequest;
import com.ecom.order.dto.ProductResponse;
import com.ecom.order.dto.UserResponse;
import com.ecom.order.model.CartItem;
import com.ecom.order.repository.CartItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // this annotation generates a constructor with required arguments
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    public boolean addToCart(String userId, CartItemRequest request) {
        // Step 1: Validate product exists via Product Service
        Optional<ProductResponse> productOpt = productServiceClient.getProductById(request.getProductId());

        if (productOpt.isEmpty()) {
            // Product not found in Product Service
            return false;
        }

        ProductResponse product = productOpt.get();

        // Step 2: Validate sufficient stock is available
        if (product.getStockQuantity() == null || product.getStockQuantity() < request.getQuantity()) {
            // Insufficient stock available
            return false;
        }

        UserResponse user = userServiceClient.getUserById(userId);
        if (user == null) {
            // User not found
            return false;
        }
        // Step 3: Check if product already exists in user's cart
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId,
                request.getProductId());

        if (existingCartItem != null) {
            // Product already in cart - update quantity
            int newQuantity = existingCartItem.getQuantity() + request.getQuantity();

            // Validate total quantity doesn't exceed available stock
            if (newQuantity > product.getStockQuantity()) {
                return false;
            }

            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setPrice(product.getPrice());
            cartItemRepository.save(existingCartItem);
        } else {
            // New product - create cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice());
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
