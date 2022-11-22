package com.backend.order.order.service.impl;

import com.backend.order.order.data.dto.request.OrderReqDTO;
import com.backend.order.order.data.dto.response.OrderDTO;
import com.backend.order.order.data.edm.KafkaProducer;
import com.backend.order.order.data.mapstruct.OrderMapper;
import com.backend.order.order.data.repository.OrderRepository;
import com.backend.order.order.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;


    @Override
    public OrderDTO createOrder(OrderReqDTO orderReqDTO) {
        orderReqDTO.setOrderId(UUID.randomUUID().toString());
        orderReqDTO.setTotalPrice(orderReqDTO.getQty() * orderReqDTO.getUnitPrice());
        orderRepository.save(OrderMapper.INSTANCE.toEntity(orderReqDTO));


        /* kafka - start */
        /* send this order to the kafka */
        kafkaProducer.send("catalog-topic", orderReqDTO);
        //orderProducer.send("orders", orderDto);
        /* kafka - end */

        return OrderMapper.INSTANCE.toDto(orderRepository.findByOrderId(orderReqDTO.getOrderId()));
    }

    @Override
    public OrderDTO getOrderByOrderId(String orderId) {
        return OrderMapper.INSTANCE.toDto(orderRepository.findByOrderId(orderId));
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(String userId) {
        return OrderMapper.INSTANCE.toDtoList(orderRepository.findByUserId(userId));
    }
}