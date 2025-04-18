package com.foodsphere.controller;

import com.foodsphere.model.Food;
import com.foodsphere.model.Users;
import com.foodsphere.service.FoodService;
import com.foodsphere.service.RestaurantService;
import com.foodsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,@RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam boolean vegetarian,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonveg,
                                                        @PathVariable Long restaurantId,
                                                        @RequestParam(required = false) String foodCategory,
                                                        @RequestHeader("Authorization")String jwt) throws Exception{

        Users user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonveg, seasonal, foodCategory);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
