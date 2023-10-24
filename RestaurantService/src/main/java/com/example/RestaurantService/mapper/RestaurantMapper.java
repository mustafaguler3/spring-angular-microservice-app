package com.example.RestaurantService.mapper;

import com.example.RestaurantService.domain.Restaurant;
import com.example.RestaurantService.dto.RestaurantDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {

    RestaurantMapper INTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant mapRestaurantDtoToRestaurant(RestaurantDto restaurantDto);

    RestaurantDto mapRestaurantToRestaurantDto(Restaurant restaurant);
}


















