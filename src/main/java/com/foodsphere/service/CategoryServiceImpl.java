package com.foodsphere.service;

import com.foodsphere.model.Category;
import com.foodsphere.model.Restaurant;
import com.foodsphere.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final RestaurantService restaurantService;
    private final CategoryRepository categoryRepository;

    @Override public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }

    @Override public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantByUserId(restaurantId);

        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override public Category findCategoryById(Long categoryId) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isEmpty())  throw new Exception("Category not found with id"+categoryId);
        
        return optionalCategory.get();
    }
}
