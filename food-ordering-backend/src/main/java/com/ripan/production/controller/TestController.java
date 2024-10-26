package com.ripan.production.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
    public ResponseEntity<String> homeController() {
        return new ResponseEntity<>("Welcome to Food Ordering Backend", HttpStatus.OK);
    }
}
