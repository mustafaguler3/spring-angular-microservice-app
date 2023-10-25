package com.example.OrderService.mapper;

import com.example.OrderService.domain.Order;
import com.example.OrderService.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order mapOrderDtoToOrder(OrderDto orderDto);
    OrderDto mapOrderToOrderDto(Order order);

}




















