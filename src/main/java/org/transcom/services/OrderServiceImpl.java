package org.transcom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.transcom.entities.Order;
import org.transcom.exceptions.OrderNotFoundException;
import org.transcom.repositories.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order saveOrder(Order order) {
        return orderRepository.findById(order.getId())
                .orElseGet(() -> orderRepository.save(order));
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order " + id + " not found"));
    }

    @Override
    public void deleteOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order " + id + " not found"));
        orderRepository.delete(order);
    }
}
