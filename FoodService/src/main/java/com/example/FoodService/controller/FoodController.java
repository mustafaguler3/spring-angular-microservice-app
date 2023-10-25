package com.example.FoodService.controller;

import com.example.FoodService.dto.FoodDto;
import com.example.FoodService.dto.FoodResponse;
import com.example.FoodService.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    FoodService foodService;

    @PostMapping("/addFood")
    public ResponseEntity<FoodDto> addFood(@RequestBody FoodDto foodDto){
        FoodDto savedFood = foodService.addFood(foodDto);
        return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
    }

    @GetMapping("/getFoodDetailAndRestaurantById/{restaurantId}")
    public ResponseEntity<FoodResponse> getRestaurantDetailWithFood(@PathVariable Integer restaurantId){
        FoodResponse foodResponse = foodService.getFoodDetails(restaurantId);
        return new ResponseEntity<>(foodResponse,HttpStatus.OK);
    }
}

















