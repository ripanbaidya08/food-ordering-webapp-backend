package com.foodsphere.controller;

import com.foodsphere.model.Restaurant;
import com.foodsphere.model.Users;
import com.foodsphere.request.CreateRestaurantRequest;
import com.foodsphere.response.MessageResponse;
import com.foodsphere.service.RestaurantService;
import com.foodsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest restaurantRequest, @RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(restaurantRequest, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest request, @RequestHeader("Authorization")String jwt, @PathVariable Long restaurantId) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(restaurantId, request);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestHeader("Authorization")String jwt, @PathVariable Long restaurantId) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        restaurantService.deleteRestaurant(restaurantId);

        MessageResponse deletionMessage = new MessageResponse("Restaurant deleted successfully !");

        return new ResponseEntity<>(deletionMessage, HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestHeader("Authorization")String jwt, @PathVariable Long restaurantId) throws Exception{

        Users user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(restaurantId);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
