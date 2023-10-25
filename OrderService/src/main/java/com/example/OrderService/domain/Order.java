package com.example.OrderService.domain;

import com.example.OrderService.dto.FoodDetails;
import com.example.OrderService.dto.RestaurantDetails;
import com.example.OrderService.dto.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("order")
public class Order {

    private Integer orderId;
    private List<FoodDetails> foodDetails;
    private RestaurantDetails restaurantDetail;
    private UserDetails userDetail;
}



















