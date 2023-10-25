package com.example.FoodService.dto;

import com.example.FoodService.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {
    private List<Food> foodList;
    private Restaurant restaurant;
}



















