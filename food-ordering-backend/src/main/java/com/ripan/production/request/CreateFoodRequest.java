package com.ripan.production.request;

import com.ripan.production.model.Category;
import com.ripan.production.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description ;
    private Long price;
    private Long restaurantId;
    private boolean seasonal;
    private boolean vegetarian;

    private Category category;

    private List<String> images;

    private List<IngredientsItem> ingredients ;
}
