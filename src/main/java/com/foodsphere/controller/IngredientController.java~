package com.ripan.production.controller;

import com.ripan.production.model.IngredientCategory;
import com.ripan.production.model.IngredientsItem;
import com.ripan.production.request.IngredientCategoryRequest;
import com.ripan.production.request.IngredientRequest;
import com.ripan.production.service.IngredientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    private final IngredientsService ingredientsService;

    @PostMapping("/category/create")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest request) throws Exception {
        IngredientCategory item = ingredientsService.createIngredientCategory(request.getName(), request.getRestaurantId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping("/items/create")
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientRequest request) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientsItem(request.getRestaurantId(), request.getName(), request.getCategoryId());

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) throws Exception {
        IngredientsItem item = ingredientsService.updateStock(id);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@PathVariable Long id) throws Exception {

        List<IngredientsItem> items = ingredientsService.findRestaurantIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long restaurantId) throws Exception {

        List<IngredientCategory> items = ingredientsService.findIngredientCategoryByRestaurantId(restaurantId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
