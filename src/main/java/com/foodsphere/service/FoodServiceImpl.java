package com.foodsphere.service;

import com.foodsphere.model.Category;
import com.foodsphere.model.Food;
import com.foodsphere.model.Restaurant;
import com.foodsphere.repository.CategoryRepository;
import com.foodsphere.repository.FoodRepository;
import com.foodsphere.repository.RestaurantRepository;
import com.foodsphere.request.CreateFoodRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;

    @Override public Food createFood(CreateFoodRequest foodRequest, Category category, Restaurant restaurant) {
        if (category.getId() == null) {
            category = categoryRepository.save(category); // Save Category if not already persisted
        }
        if (restaurant.getId() == null) {
            restaurant = restaurantRepository.save(restaurant); // Save Restaurant if not already persisted
        }

        Food food = new Food();

        food.setFoodCategory(category);
        food.setRestaurant(restaurant);

        food.setName(foodRequest.getName());
        food.setDescription(foodRequest.getDescription());
        food.setPrice(foodRequest.getPrice());
        food.setImages(foodRequest.getImages());
        food.setIngredients(foodRequest.getIngredients());
        food.setSeasonal(foodRequest.isSeasonal());
        food.setVegetarian(foodRequest.isVegetarian());

        Food persistedFood = foodRepository.save(food);

        restaurant.getFoods().add(persistedFood);

        return persistedFood;
    }

    @Override public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.delete(food); // save(food)
    }

    @Override public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if(isVegetarian) foods = filterByVegetarian(foods, isVegetarian);
        if(isNonVeg) foods = filterByNonVeg(foods, isNonVeg);
        if (isSeasonal) foods = filterBySeasonal(foods, isSeasonal);

        if(foodCategory != null && !foodCategory.equals("")){
            foods = filterByCategory(foods, foodCategory);
        }

        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory() != null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) { // isNonVeg = false
        return foods.stream().filter(food -> food.isVegetarian()!=isNonVeg).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }

    @Override public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if(optionalFood.isEmpty())  throw new Exception("food not found with id : " + foodId);

        return optionalFood.get();
    }

    @Override public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());

        return foodRepository.save(food);
    }
}
