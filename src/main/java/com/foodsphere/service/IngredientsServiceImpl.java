package com.foodsphere.service;

import com.foodsphere.model.IngredientCategory;
import com.foodsphere.model.IngredientsItem;
import com.foodsphere.model.Restaurant;
import com.foodsphere.repository.IngredientCategoryRepository;
import com.foodsphere.repository.IngredientsItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientsServiceImpl implements IngredientsService{

    private final IngredientsItemRepository ingredientsItemRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final RestaurantService restaurantService;


    @Override public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = new IngredientCategory();

        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientCategoryRepository.save(category);
    }

    @Override public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> optional = ingredientCategoryRepository.findById(id);
        if(optional.isEmpty())  throw new Exception("Ingredient Category not found !");

        return optional.get();
    }

    @Override public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();

        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredients = ingredientsItemRepository.save(item);
        category.getIngredients().add(ingredients);

        return ingredients;
    }

    @Override public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingredientsItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty())   throw new Exception("Ingredient Item not found !");

        IngredientsItem ingredientsItem = optionalIngredientsItem.get();
        ingredientsItem.setStoke(!ingredientsItem.isStoke());

        return ingredientsItemRepository.save(ingredientsItem);
    }
}
