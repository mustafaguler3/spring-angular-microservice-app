package com.example.OrderService.controller;

import com.example.OrderService.OrderServiceConfig;
import com.example.OrderService.domain.Order;
import com.example.OrderService.model.OrderRequest;
import com.example.OrderService.model.OrderResponse;
import com.example.OrderService.model.PaymentMode;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.util.StreamUtils.copyToString;


@SpringBootTest({"server.port=0"})
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = {OrderServiceConfig.class})
public class OrderControllerTest {

    @Mock
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    static WireMockExtension wireMockServer
            = WireMockExtension.newInstance()
            .options(WireMockConfiguration
                    .wireMockConfig()
                    .port(8080))
            .build();

    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

    @BeforeEach
    void Setup() throws IOException {
        getProductDetailsResponse();
        doPayment();
        getPaymentDetails();
        reduceQuantity();
    }

    private OrderRequest getMockOrderRequest(){
        return OrderRequest.builder()
                .productId(1)
                .paymentMode(PaymentMode.CASH)
                .quantity(10)
                .totalAmount(100)
                .build();

    }

    @Test
    void test_WhenPlaceOrder_DoPayment_Success() throws Exception {
        //First Place Order

        //Get Order By OrderId from db and check

        // check output
        OrderRequest orderRequest = getMockOrderRequest();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/order/placeOrder")
                .with(jwt().authorities(new SimpleGrantedAuthority("Customer")))
                .content(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String orderId = mvcResult.getResponse().getContentAsString();

        Optional<Order> order = orderRepository.findById(Long.valueOf(orderId));
        assertTrue(order.isPresent());

        Order o = order.get();
        assertEquals(Long.parseLong(orderId),o.getId());
        assertEquals("PLACED",o.getOrderStatus());
        assertEquals(orderRequest.getTotalAmount(),o.getAmount());
    }

    @Test
    void test_WhenPlaceOrderWithWrongAccess_thenThrow403() throws Exception {
        OrderRequest orderRequest = getMockOrderRequest();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/order/placeOrder")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin")))
                        .content(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(MockMvcResultMatchers.status().isForbidden()).andReturn();
    }

    private void reduceQuantity() {
        wireMockServer.stubFor(WireMock.put(WireMock.urlMatching("/product/reduceQuantity/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type",MediaType.APPLICATION_JSON_VALUE)));
    }

    private void getPaymentDetails() throws IOException {
        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching("/payment/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type",MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(OrderControllerTest.class.getClassLoader().getResourceAsStream("mock/GetPayment.json"),Charset.defaultCharset()))));
    }

    private void doPayment() {
        wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/payment"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type",MediaType.APPLICATION_JSON_VALUE)));

    }

    private void getProductDetailsResponse() throws IOException {
        // GET /product/1
        wireMockServer.stubFor(WireMock.get(("/product/1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(copyToString(OrderControllerTest.class.getClassLoader().getResourceAsStream("mock/GetProduct.json"),
                        Charset.defaultCharset()))));
    }

    @Test
    public void test_WhenGetOrder_Success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                "/order/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin")))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        Order order = orderRepository.findById(1L).get();
        String expectedResponse = getOrderResponse(order);

        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    public void test_WhenGetOrder_Order_Not_Found() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/order/6")
                        .with(jwt().authorities(new SimpleGrantedAuthority("Admin")))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    }

    private String getOrderResponse(Order order) throws IOException {
        OrderResponse.PaymentDetails paymentDetails = objectMapper.readValue(copyToString(OrderControllerTest.class.getClassLoader().getResourceAsStream("/mock/GetPayment.json"),Charset.defaultCharset()), OrderResponse.PaymentDetails.class);

        paymentDetails.setPaymentStatus("SUCCESS");

        OrderResponse.ProductDetails productDetails = objectMapper.readValue(copyToString(OrderControllerTest.class.getClassLoader().getResourceAsStream("/mock/GetProduct.json"),Charset.defaultCharset()), OrderResponse.ProductDetails.class);

        OrderResponse orderResponse = OrderResponse.builder()
                .paymentDetails(paymentDetails)
                .productDetails(productDetails)
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .orderId(order.getId())
                .build();

        return objectMapper.writeValueAsString(orderResponse);
    }
}




















