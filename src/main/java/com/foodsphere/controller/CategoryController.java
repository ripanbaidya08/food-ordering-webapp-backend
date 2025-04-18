package com.foodsphere.controller;

import com.foodsphere.model.Category;
import com.foodsphere.model.Users;
import com.foodsphere.service.CategoryService;
import com.foodsphere.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/admin/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);

        Category creatdCategory = categoryService.createCategory(category.getName(), user.getId());

        return new ResponseEntity<>(creatdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> findCategoryByRestaurantId(@RequestHeader("Authorization")String jwt) throws Exception{
        Users user = userService.findUserByJwtToken(jwt);
        List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId()); // this might restaurant owner id. check this

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public Category findCategoryById(@RequestHeader("Authorization") String jwt ,@PathVariable Long categoryId) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);

        return categoryService.findCategoryById(categoryId);
    }



}
