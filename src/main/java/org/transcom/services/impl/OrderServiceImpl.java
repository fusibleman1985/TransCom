package org.transcom.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.dto.OrderDtoResponse;
import org.transcom.entities.Order;
import org.transcom.entities.enums.OrderStatus;
import org.transcom.exceptions.OrderNotFoundException;
import org.transcom.mappers.OrderMapper;
import org.transcom.repositories.FavoriteRepository;
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
    private final FavoriteRepository favoriteRepository;

    @Override
    public Order saveOrder(OrderDtoRequest orderDtoRequest) {
        return orderRepository.save(orderMapper.toOrderDtoResponse(orderDtoRequest, userRepository));
    }

    @Override
    public List<OrderDtoResponse> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(o -> orderMapper.toOrderDtoResponse(o, favoriteRepository))
                .toList();
    }

    @Override
    public OrderDtoResponse findOrderById(UUID id) {
        Order orderById = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("{error.order_not_found}"));
        return orderMapper.toOrderDtoResponse(orderById, favoriteRepository);
    }

    @Override
    @Transactional
    public OrderDtoResponse updateOrder(UUID id, OrderDtoRequest orderDtoRequest) {
        Order orderToUpdate = findOrderById(id);
        orderMapper.updatedEntityFromDto(orderDtoRequest, orderToUpdate, userRepository);
        Order savedOrder = orderRepository.save(orderToUpdate);
        return orderMapper.toOrderDtoResponse(savedOrder, favoriteRepository);
    }

    @Override
    @Transactional
    public boolean deleteOrder(UUID id) {
        Order orderById = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("{error.order_not_found}"));
        if (orderById.getOrderStatus() != OrderStatus.DELETED) {
            orderById.setOrderStatus(OrderStatus.DELETED);
            orderRepository.save(orderById);
            return true;
        }
        return false;
    }
}