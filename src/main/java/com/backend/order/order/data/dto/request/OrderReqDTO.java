package com.backend.order.order.data.dto.request;

import lombok.Data;

@Data
public class OrderReqDTO {
    private String productId;

    private Integer qty;
    private Integer unitPrice;

    private String userId;

    private String orderId;
    private Integer totalPrice;
}
