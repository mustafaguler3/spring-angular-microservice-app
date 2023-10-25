package com.example.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDetails {
    private int id;
    private String foodName;
    private String description;
    private boolean isVeg;
    private int price;
    private Integer restaurantId;
    private Integer quantity;
}
















