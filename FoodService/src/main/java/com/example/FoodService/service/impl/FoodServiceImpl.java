package com.example.FoodService.service.impl;

import com.example.FoodService.domain.Food;
import com.example.FoodService.dto.FoodDto;
import com.example.FoodService.dto.FoodResponse;
import com.example.FoodService.dto.Restaurant;
import com.example.FoodService.mapper.FoodMapper;
import com.example.FoodService.repository.FoodRepository;
import com.example.FoodService.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public FoodDto addFood(FoodDto foodDto) {
        Food food = foodRepository.save(FoodMapper.INSTANCE.mapFoodDtoToFood(foodDto));
        return FoodMapper.INSTANCE.mapFoodToFoodDto(food);
    }

    @Override
    public FoodResponse getFoodDetails(Integer restaurantId) {

        List<Food> foodList = getFoodList(restaurantId);
        Restaurant restaurant = getRestaurantDetails(restaurantId);
        return createFoodResponse(foodList,restaurant);
    }

    private FoodResponse createFoodResponse(List<Food> foodList, Restaurant restaurant) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setFoodList(foodList);
        foodResponse.setRestaurant(restaurant);

        return foodResponse;
    }

    private Restaurant getRestaurantDetails(Integer restaurantId) {
        Restaurant restaurant = restTemplate.getForObject("http://RESTAURANT-SERVICE/getById/"+restaurantId,Restaurant.class);

        return restaurant;
    }

    private List<Food> getFoodList(Integer restaurantId) {
        return foodRepository.findByRestaurantId(restaurantId);
    }

}

















