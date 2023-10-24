package com.example.RestaurantService.service;

import com.example.RestaurantService.dto.RestaurantDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> findAllRestaurants();

    RestaurantDto addRestaurant(RestaurantDto restaurantDto);

    ResponseEntity<RestaurantDto> findRestaurantById(Integer id);
}
