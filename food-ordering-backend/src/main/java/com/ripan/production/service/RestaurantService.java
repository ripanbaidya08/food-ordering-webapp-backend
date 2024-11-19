package com.ripan.production.service;

import com.ripan.production.dto.RestaurantDto;
import com.ripan.production.exception.RestaurantException;
import com.ripan.production.model.Restaurant;
import com.ripan.production.model.User;
import com.ripan.production.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user);

    Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurantRequest) throws RestaurantException;

    void deleteRestaurant(Long restaurantId) throws RestaurantException;

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findRestaurantById(Long restaurantId) throws RestaurantException;

    Restaurant findRestaurantByUserId(Long userId) throws RestaurantException;

    RestaurantDto addToFavourites(Long restaurantId, User user) throws RestaurantException;

    Restaurant updateRestaurantStatus(Long restaurantId) throws RestaurantException; // user can open / close the restaurant
}
