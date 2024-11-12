package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.entities.Order;
import org.transcom.entities.enums.OrderStatus;
import org.transcom.exceptions.OrderNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.mapper.OrderMapper;
import org.transcom.repositories.OrderRepository;
import org.transcom.services.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order saveOrder(@Valid OrderDtoRequest orderDtoRequest) {
        Order order = orderMapper.toEntity(orderDtoRequest);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException(ErrorMessages.ORDER_NOT_FOUND.getMessage()));
    }

    @Override
    public Order updateOrder(OrderDtoRequest orderDtoRequest, UUID id) {
        Order orderToUpdate = findOrderById(id);
        Order updatedOrder = orderMapper.updatedEntityFromDto(orderDtoRequest, orderToUpdate);
        return orderRepository.save(updatedOrder);
    }

    @Override
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setOrderStatus(OrderStatus.DELETED);
            orderRepository.save(order);
        }
    }
}