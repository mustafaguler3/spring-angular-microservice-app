package com.example.OrderService.service.impl;

import com.example.OrderService.domain.Order;
import com.example.OrderService.external.client.ProductService;
import com.example.OrderService.model.OrderRequest;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //Order entity -> save the data wtih status order created
        //Product service -> block products (reduce the quantity)
        //payment service -> payments -> Success -> Complete,else
        //Cancelled
        log.info("Placing order requests: {}",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Creating order with status CREATED ");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("order place successfully with order id :{}",order.getId());

        return order.getId();
    }
}









































