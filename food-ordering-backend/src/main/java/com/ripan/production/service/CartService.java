package com.ripan.production.service;

import com.ripan.production.model.Cart;
import com.ripan.production.model.CartItem;
import com.ripan.production.request.AddCartItemRequest;

public interface CartService {

    CartItem addItemToCart(AddCartItemRequest request, String jwt )throws Exception;

    CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    Long calculateCartTotals(Cart cart) throws Exception;

    Cart findCartById(Long id) throws Exception;

    Cart findCartByUserId(Long userId) throws Exception;

    Cart clearCart(Long userId) throws Exception;

}
