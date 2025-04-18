package com.foodsphere.service;

import com.foodsphere.model.Order;
import com.foodsphere.model.Users;
import com.foodsphere.request.OrderRequest;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest order, Users user) throws Exception;

    List<Order> getUsersOrder(Long userId) throws Exception;

    void cancelOrder(Long orderId) throws Exception;

    Order updateOrderStatus(Long orderId, String orderStatus) throws Exception;

    List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception;

    Order findOrderById(Long orderId) throws Exception;

}
