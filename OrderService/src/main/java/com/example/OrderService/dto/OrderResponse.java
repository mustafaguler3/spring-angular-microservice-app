package com.example.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private List<FoodDetails> foodDetails;
    private Integer userId;
    private RestaurantDetails restaurantDetail;
}




















