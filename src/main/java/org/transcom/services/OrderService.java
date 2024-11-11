package org.transcom.services;

import org.transcom.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<OrderDto> findAllOrders();

    OrderDto findOrderById(UUID id);

    OrderDto saveOrder(OrderDto orderDto);

    OrderDto updateOrder(OrderDto orderDto);

    void deleteOrder(UUID id);
}
