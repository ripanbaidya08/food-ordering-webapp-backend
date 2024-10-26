package com.ripan.production.service;

import com.ripan.production.model.Order;
import com.ripan.production.model.User;
import com.ripan.production.request.OrderRequest;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest order, User user) throws Exception;

    List<Order> getUsersOrder(Long userId) throws Exception;

    void cancelOrder(Long orderId) throws Exception;

    Order updateOrderStatus(Long orderId, String orderStatus) throws Exception;

    List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception;

    Order findOrderById(Long orderId) throws Exception;

}
