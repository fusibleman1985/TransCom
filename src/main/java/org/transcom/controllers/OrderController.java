package org.transcom.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.dto.OrderDtoResponse;
import org.transcom.services.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDtoResponse> saveOrder(@RequestBody @Valid OrderDtoRequest orderDtoRequest) {
        OrderDtoResponse savedOrder = orderService.saveOrder(orderDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<OrderDtoResponse>> findAllOrders() {
        List<OrderDtoResponse> orders = orderService.findAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<OrderDtoResponse> findOrderById(@PathVariable UUID id) {
        OrderDtoResponse order = orderService.findOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDtoResponse> updateOrder(@PathVariable UUID id, @RequestBody @Valid OrderDtoRequest orderDtoRequest) {
        OrderDtoResponse updatedOrder = orderService.updateOrder(id, orderDtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
