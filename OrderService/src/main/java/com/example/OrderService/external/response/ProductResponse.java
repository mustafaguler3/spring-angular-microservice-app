package com.example.OrderService.external.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private String productName;
    private long productId;
    private long price;
    private long quantity;

    public ProductResponse() {
    }

    public ProductResponse(String productName, long productId, long price, long quantity) {
        this.productName = productName;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }
}
