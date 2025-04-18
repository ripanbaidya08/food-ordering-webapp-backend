package com.foodsphere.service;

import com.foodsphere.dto.RestaurantDto;
import com.foodsphere.exception.RestaurantException;
import com.foodsphere.model.Restaurant;
import com.foodsphere.model.Users;
import com.foodsphere.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, Users user);

    Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurantRequest) throws RestaurantException;

    void deleteRestaurant(Long restaurantId) throws RestaurantException;

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findRestaurantById(Long restaurantId) throws RestaurantException;

    Restaurant findRestaurantByUserId(Long userId) throws RestaurantException;

    RestaurantDto addToFavourites(Long restaurantId, Users user) throws RestaurantException;

    Restaurant updateRestaurantStatus(Long restaurantId) throws RestaurantException; // user can open / close the restaurant
}
