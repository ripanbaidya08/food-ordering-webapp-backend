package com.ripan.production.service;

import com.ripan.production.model.IngredientCategory;
import com.ripan.production.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    IngredientsItem updateStock(Long id) throws Exception;
}
