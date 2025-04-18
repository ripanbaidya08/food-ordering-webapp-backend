package com.foodsphere.service;

import com.foodsphere.model.Category;
import com.foodsphere.model.Food;
import com.foodsphere.model.Restaurant;
import com.foodsphere.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    List<Food> getRestaurantsFood(Long restaurantId, boolean isVagetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory);

    List<Food> searchFood(String keyword);

    Food findFoodById(Long foodId) throws Exception;

    Food updateAvailabilityStatus(Long foodId) throws Exception;
}
