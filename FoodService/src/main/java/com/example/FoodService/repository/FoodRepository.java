package com.example.FoodService.repository;

import com.example.FoodService.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {
    List<Food> findByRestaurantId(Integer restaurantId);
}
