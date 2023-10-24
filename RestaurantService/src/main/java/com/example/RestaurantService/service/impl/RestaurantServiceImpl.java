package com.example.RestaurantService.service.impl;

import com.example.RestaurantService.domain.Restaurant;
import com.example.RestaurantService.dto.RestaurantDto;
import com.example.RestaurantService.mapper.RestaurantMapper;
import com.example.RestaurantService.repository.RestaurantRepository;
import com.example.RestaurantService.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<RestaurantDto> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        //map it to list of dtos
        List<RestaurantDto> restaurantsDtos = restaurants.stream().map(restaurant -> RestaurantMapper.INTANCE.mapRestaurantToRestaurantDto(restaurant)).collect(Collectors.toList());

        return restaurantsDtos;
    }

    @Override
    public RestaurantDto addRestaurant(RestaurantDto restaurantDto) {
        Restaurant savedRestaurant = restaurantRepository.save(RestaurantMapper.INTANCE.mapRestaurantDtoToRestaurant(restaurantDto));

        return RestaurantMapper.INTANCE.mapRestaurantToRestaurantDto(savedRestaurant);
    }

    @Override
    public ResponseEntity<RestaurantDto> findRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if (restaurant.isPresent()){
            return new ResponseEntity<>(RestaurantMapper.INTANCE.mapRestaurantToRestaurantDto(restaurant.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
}














