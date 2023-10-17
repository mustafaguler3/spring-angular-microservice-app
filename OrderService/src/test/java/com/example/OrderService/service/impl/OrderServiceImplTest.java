package com.example.OrderService.service.impl;

import com.example.OrderService.domain.Order;
import com.example.OrderService.external.client.PaymentService;
import com.example.OrderService.external.client.ProductService;
import com.example.OrderService.external.response.PaymentResponse;
import com.example.OrderService.external.response.ProductResponse;
import com.example.OrderService.model.OrderResponse;
import com.example.OrderService.model.PaymentMode;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @DisplayName("Get Order - Success Scenario")
    @Test
    void test_When_Order_Success(){
        //Mocking
        Order order = getMockOrder();
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));
        
        when(restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(),ProductResponse.class)).thenReturn(getMockProductResponse());

        when(restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/"+order.getId(),PaymentResponse.class)).thenReturn(getMockPaymentResponse());

        //Actual
        orderService.getOrderDetails(1);
        OrderResponse orderResponse = orderService.getOrderDetails(1);

        //Verification
        verify(orderRepository,times(1)).findById(anyLong());
        verify(restTemplate,times(1)).getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(),ProductResponse.class);
        verify(restTemplate,times(1)).getForObject("http://PAYMENT-SERVICE/payment/order/"+order.getId(),PaymentResponse.class);
        //Assert
        assertNotNull(orderResponse);
        assertNotNull(order.getId(), String.valueOf(orderResponse.getOrderId()));
    }


    @Test
    void test_When_Get_Order_NOT_FOUND_then_Not_Found(){

    }

    private PaymentResponse getMockPaymentResponse(){
        return PaymentResponse.builder()
                .paymentId(1)
                .paymentDate(Instant.now())
                .paymentMode(PaymentMode.CASH)
                .amount(200)
                .orderId(1)
                .status("ACCEPTED")
                .build();
    }

    private ProductResponse getMockProductResponse() {
        return ProductResponse.builder()
                .productId(2)
                .productName("iphone")
                .price(100)
                .quantity(100)
                .build();
    }

    private Order getMockOrder() {
        return Order.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .id(1)
                .amount(100)
                .quantity(200)
                .productId(1)
                .build();
    }
}



















