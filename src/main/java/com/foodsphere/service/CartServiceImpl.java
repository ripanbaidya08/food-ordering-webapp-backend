package com.foodsphere.service;

import com.foodsphere.model.Cart;
import com.foodsphere.model.CartItem;
import com.foodsphere.model.Food;
import com.foodsphere.model.Users;
import com.foodsphere.repository.CartItemRepository;
import com.foodsphere.repository.CartRepository;
import com.foodsphere.request.AddCartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final FoodService foodService;

    @Override public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem : cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity() + request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }
        CartItem newCartItem = new CartItem();

        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setIngredients(request.getIngredients());
        newCartItem.setTotalPrice(food.getPrice() * request.getQuantity());

        CartItem savedCartItem =cartItemRepository.save(newCartItem);

        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if(optionalCartItem.isEmpty())  throw new Exception("Cart Item not found with id"+cartItemId);

        CartItem item = optionalCartItem.get();

        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);

        return cartItemRepository.save(item);
    }

    @Override public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);

        if(optionalCartItem.isEmpty())  throw new Exception("Cart Item not Found !");

        CartItem item = optionalCartItem.get();
        cart.getItems().remove(item);

        return cartRepository.save(cart);
    }

    @Override public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;

        for(CartItem cartItem : cart.getItems()){
            total += cartItem.getFood().getPrice() * cartItem.getTotalPrice();
        }
        return total;
    }

    @Override public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isEmpty())  throw new Exception("Cart Not found with id : " + id);

        return optionalCart.get();
    }

    @Override public Cart findCartByUserId(Long userId) throws Exception {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));

        return cart;
    }

    @Override public Cart clearCart(Long userId) throws Exception {
        Cart cart = findCartByUserId(userId);
        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
