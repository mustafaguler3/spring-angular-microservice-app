package com.example.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer orderId;
    private List<FoodDetails> foodDetails;
    private RestaurantDetails restaurantDetail;
    private UserDetails userDetail;
}
