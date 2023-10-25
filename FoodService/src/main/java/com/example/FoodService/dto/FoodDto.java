package com.example.FoodService.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodDto {

    private int id;
    private String foodName;
    private boolean isVeg;
    private int price;
    private Integer restaurantId;
    private Integer quantity;

}
