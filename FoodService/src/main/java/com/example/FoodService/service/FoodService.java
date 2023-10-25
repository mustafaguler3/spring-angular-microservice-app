package com.example.FoodService.service;

import com.example.FoodService.dto.FoodDto;
import com.example.FoodService.dto.FoodResponse;

public interface FoodService {
    FoodDto addFood(FoodDto foodDto);

    FoodResponse getFoodDetails(Integer restaurantId);
}
















