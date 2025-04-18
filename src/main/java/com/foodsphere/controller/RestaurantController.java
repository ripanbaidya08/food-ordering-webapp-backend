package com.foodsphere.controller;

import com.foodsphere.dto.RestaurantDto;
import com.foodsphere.model.Restaurant;
import com.foodsphere.model.Users;
import com.foodsphere.service.RestaurantService;
import com.foodsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization")String jwt, @RequestParam String keyword) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.getAllRestaurant();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization")String jwt, @PathVariable Long restaurantId) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}/add-favourites")
    public ResponseEntity<RestaurantDto> addToFavourites(@RequestHeader("Authorization")String jwt, @PathVariable Long restaurantId) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        RestaurantDto restaurant = restaurantService.addToFavourites(restaurantId, user);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
