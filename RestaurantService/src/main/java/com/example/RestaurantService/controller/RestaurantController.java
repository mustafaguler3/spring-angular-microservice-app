package com.example.RestaurantService.controller;

import com.example.RestaurantService.dto.RestaurantDto;
import com.example.RestaurantService.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/getAllRestaurants")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(){
        List<RestaurantDto> restaurantDtos = restaurantService.findAllRestaurants();
        return new ResponseEntity<>(restaurantDtos, HttpStatus.OK);
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<RestaurantDto> saveRestaurant(@RequestBody RestaurantDto restaurantDto){
        RestaurantDto restaurant = restaurantService.addRestaurant(restaurantDto);
        return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<RestaurantDto> findRestaurantById(@PathVariable("id") Integer id){
        return restaurantService.findRestaurantById(id);
    }
}















