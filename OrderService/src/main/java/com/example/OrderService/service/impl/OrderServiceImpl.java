package com.example.OrderService.service.impl;

import com.example.OrderService.domain.Order;
import com.example.OrderService.dto.OrderDto;
import com.example.OrderService.dto.UserDetails;
import com.example.OrderService.mapper.OrderMapper;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import com.example.OrderService.service.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    SequenceGenerator sequenceGenerator;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        Integer newOrderId = sequenceGenerator.generateNextOrder();

        UserDetails userDetails = getUserDetailsFromUserId(orderDto.getUserDetail().getUserId());

        Order orderSaved = new Order(newOrderId,orderDto.getFoodDetails(),orderDto.getRestaurantDetail(),orderDto.getUserDetail());
        orderRepository.save(orderSaved);

        return OrderMapper.INSTANCE.mapOrderToOrderDto(orderSaved);
    }

    private UserDetails getUserDetailsFromUserId(int userId) {
        return restTemplate.getForObject("http://USER-SERVICE/user/getUserById/"+userId,UserDetails.class);
    }
}





















