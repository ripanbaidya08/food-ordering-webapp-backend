package com.foodsphere.controller;

import com.foodsphere.model.Cart;
import com.foodsphere.model.CartItem;
import com.foodsphere.model.Users;
import com.foodsphere.request.AddCartItemRequest;
import com.foodsphere.request.UpdateCartItemRequest;
import com.foodsphere.service.CartService;
import com.foodsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;


    @PostMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request, @RequestHeader("Authorization")String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(request, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest request, @RequestHeader("Authorization")String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id, @RequestHeader("Authorization")String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(id,jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization")String jwt) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);

        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }



}
