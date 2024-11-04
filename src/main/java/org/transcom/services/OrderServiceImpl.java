package org.transcom.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.transcom.entities.Order;
import org.transcom.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(UUID id) {
        if (orderRepository.findById(id).isPresent()) {
            return orderRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Order saveOrder(Order order) {
        if (orderRepository.findById(order.getId()).isPresent()) {
            return orderRepository.findById(order.getId()).get();
        }
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID id) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order not found");
        }
    }
}
