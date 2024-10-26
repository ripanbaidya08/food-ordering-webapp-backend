package com.ripan.production.service;

import com.ripan.production.dto.RestaurantDto;
import com.ripan.production.model.Restaurant;
import com.ripan.production.model.User;
import com.ripan.production.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user);

    Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurantRequest) throws Exception;

    void deleteRestaurant(Long restaurantId) throws Exception;

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findRestaurantById(Long restaurantId) throws Exception;

    Restaurant findRestaurantByUserId(Long userId) throws Exception;

    RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;

    Restaurant updateRestaurantStatus(Long restaurantId) throws Exception; // user can open / close the restaurant
}
