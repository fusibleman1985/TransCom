package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.entities.Order;
import org.transcom.entities.User;
import org.transcom.entities.enums.OrderStatus;
import org.transcom.exceptions.OrderNotFoundException;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.mappers.OrderMapper;
import org.transcom.repositories.OrderRepository;
import org.transcom.repositories.UserRepository;
import org.transcom.services.OrderService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Override
    public Order saveOrder(OrderDtoRequest orderDtoRequest) {
        User user = userRepository.findById(orderDtoRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage()));
        Order order = orderMapper.toEntity(orderDtoRequest, user);
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
    public Order updateOrder(UUID id,OrderDtoRequest orderDtoRequest) {
        Order orderToUpdate = findOrderById(id);
        User user = userRepository.findById(orderDtoRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage()));
        orderMapper.updatedEntityFromDto(orderDtoRequest, orderToUpdate, user);
        return orderRepository.save(orderToUpdate);
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