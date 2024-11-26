package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.dto.OrderDtoResponse;
import org.transcom.entities.Order;
import org.transcom.entities.enums.OrderStatus;
import org.transcom.exceptions.OrderNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.mappers.OrderMapper;
import org.transcom.repositories.OrderRepository;
import org.transcom.repositories.UserRepository;
import org.transcom.services.OrderService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Override
    public OrderDtoResponse saveOrder(OrderDtoRequest orderDtoRequest) {
        Order order = orderMapper.toEntity(orderDtoRequest, userRepository);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDtoResponse(savedOrder);
    }

    @Override
    public List<OrderDtoResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toOrderDtoResponseWithUserCheck)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDtoResponse findOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ErrorMessages.ORDER_NOT_FOUND.getMessage()));
        return orderMapper.toOrderDtoResponse(order);
    }

    @Override
    public OrderDtoResponse updateOrder(UUID id, OrderDtoRequest orderDtoRequest) {
        Order orderToUpdate = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(ErrorMessages.ORDER_NOT_FOUND.getMessage()));
        orderMapper.updateEntityFromDto(orderDtoRequest, orderToUpdate, userRepository);
        Order updatedOrder = orderRepository.save(orderToUpdate);
        return orderMapper.toOrderDtoResponse(updatedOrder);
    }

    @Override
    public boolean deleteOrder(UUID id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setOrderStatus(OrderStatus.DELETED);
            orderRepository.save(order);
            return true;
        }
        return false;
    }
}
