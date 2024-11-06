package org.transcom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transcom.entities.Order;
import org.transcom.entities.User;
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

//    @Transactional
//    @Override
//    public void deleteOrder(UUID id) {
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new OrderNotFoundException("Order " + id + " not found"));
//
//        User user = order.getUser();
//        if (user != null) {
//            user.getOrders().remove(order);  // Убираем заказ из списка заказов пользователя
//        }
//
//        orderRepository.delete(order);
//    }
}
