package com.example.FoodService.mapper;

import com.example.FoodService.domain.Food;
import com.example.FoodService.dto.FoodDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FoodMapper {

    FoodMapper INSTANCE = Mappers.getMapper(FoodMapper.class);

    Food mapFoodDtoToFood(FoodDto foodDto);

    FoodDto mapFoodToFoodDto(Food food);
}

















