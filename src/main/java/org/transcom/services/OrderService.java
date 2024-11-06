package org.transcom.services;

import org.transcom.entities.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<Order> findAllOrders();

    Order findOrderById(UUID id);

    Order saveOrder(Order order);

//    void deleteOrder(UUID id);
}
