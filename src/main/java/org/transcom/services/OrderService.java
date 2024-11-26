package org.transcom.services;

import org.transcom.dto.OrderDtoRequest;
import org.transcom.dto.OrderDtoResponse;
import org.transcom.entities.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order saveOrder(OrderDtoRequest orderDtoRequest);

    List<OrderDtoResponse> findAllOrders();

    OrderDtoResponse findOrderById(UUID id);

    OrderDtoResponse updateOrder(UUID id, OrderDtoRequest orderDtoRequest);

    boolean deleteOrder(UUID id);
}
