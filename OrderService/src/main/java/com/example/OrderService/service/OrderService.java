package com.example.OrderService.service;

import com.example.OrderService.dto.OrderDto;

public interface OrderService {
    OrderDto saveOrder(OrderDto orderDto);
}
