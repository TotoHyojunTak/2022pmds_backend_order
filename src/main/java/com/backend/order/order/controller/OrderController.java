package com.backend.order.order.controller;

import com.backend.order.order.data.dto.request.OrderReqDTO;
import com.backend.order.order.data.dto.response.OrderDTO;
import com.backend.order.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final Environment env;
    private final OrderService orderService;
    //private final KafkaProducer kafkaProducer;


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable("userId") String userId,
                                                @RequestBody OrderReqDTO orderReqDTO) {
        log.info("Before add orders data");
        orderReqDTO.setUserId(userId);
        OrderDTO createdOrder = orderService.createOrder(orderReqDTO);

        log.info("After added orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PostMapping("/feign/{userId}/orders")
    public ResponseEntity<?> createOrderForFeign(@PathVariable("userId") String userId
                                                , @RequestBody Map<String, String> input) {
        log.info("Before add orders data");

        OrderReqDTO orderReqDTO = new OrderReqDTO();

        orderReqDTO.setProductId(input.get("productId"));
        orderReqDTO.setQty(Integer.valueOf(input.get("stock")));
        orderReqDTO.setUnitPrice(Integer.valueOf(input.get("unitPrice")));
        orderReqDTO.setUserId(userId);
        OrderDTO createdOrder = orderService.createOrder(orderReqDTO);

        log.info("After added orders data");
        return new ResponseEntity<>(createdOrder, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrder(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/feign/{userId}/list")
    public ResponseEntity<List<Map<String, String>>> getOrderListForFeign(@PathVariable("userId") String userId){
        List<Map<String, String>> returnValue = new ArrayList<>();

        List<OrderDTO> result = orderService.getOrdersByUserId(userId);
        result.forEach(e->{

            Map<String, String> temp = new HashMap<>();
            temp.put("orderId", e.getOrderId());
            temp.put("produceId", e.getProductId());
            temp.put("qty", String.valueOf(e.getQty()));
            temp.put("unitPrice", String.valueOf(e.getUnitPrice()));
            temp.put("totalPrice", String.valueOf(e.getTotalPrice()));

            returnValue.add(temp);
        });

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}