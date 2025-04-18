package com.foodsphere.controller;

import com.foodsphere.model.Food;
import com.foodsphere.model.Restaurant;
import com.foodsphere.model.Users;
import com.foodsphere.request.CreateFoodRequest;
import com.foodsphere.response.MessageResponse;
import com.foodsphere.service.FoodService;
import com.foodsphere.service.RestaurantService;
import com.foodsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    private final FoodService foodService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request, @RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.createFood(request, request.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long foodId, @RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(foodId);

        MessageResponse deleteFoodMessageResponse = new MessageResponse("Food deleted successfully !");

        return new ResponseEntity<>(deleteFoodMessageResponse, HttpStatus.OK);
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long foodId, @RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(foodId);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
