package org.transcom.services;

import org.transcom.dto.OrderDtoRequest;
import org.transcom.entities.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order saveOrder(OrderDtoRequest orderDtoRequest);

    List<Order> findAllOrders();

    Order findOrderById(UUID id);

    Order updateOrder(OrderDtoRequest orderDtoRequest, UUID id);

    void deleteOrder(UUID id);
}
