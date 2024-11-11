package org.transcom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transcom.dto.OrderDto;
import org.transcom.entities.Order;
import org.transcom.entities.User;
import org.transcom.exceptions.OrderNotFoundException;
import org.transcom.mapper.OrderMapper;
import org.transcom.repositories.OrderRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public List<OrderDto> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.mapToListDto(orders);
    }

    @Transactional
    @Override
    public OrderDto findOrderById(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id.toString()));
        return orderMapper.mapToDto(order);
    }

    @Transactional
    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        if (order.getId() == null) {
            order.setId(UUID.randomUUID());
        }
        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapToDto(savedOrder);
    }

    @Transactional
    @Override
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order " + id + " not found"));
        User user = order.getUser();
        if (user != null) {
            user.getOrders().remove(order);
        }
        orderRepository.delete(order);
    }
}
