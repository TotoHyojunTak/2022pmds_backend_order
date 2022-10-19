package com.backend.order.order.service;

import com.backend.order.order.data.dto.request.OrderReqDTO;
import com.backend.order.order.data.dto.response.OrderDTO;
import com.backend.order.order.data.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderReqDTO orderReqDTO);
    OrderDTO getOrderByOrderId(String orderId);
    List<OrderDTO> getOrdersByUserId(String userId);
}