package org.transcom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.dto.OrderDtoResponse;
import org.transcom.entities.Order;
import org.transcom.services.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
@Tag(name = "Order Controller", description = "Controller for managing orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "Create a new order", description = "Creates a new order based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Order> saveOrder(@RequestBody @Valid OrderDtoRequest orderDtoRequest) {
        Order savedOrder = orderService.saveOrder(orderDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all orders", description = "Returns a list of all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders successfully retrieved")
    })
    public ResponseEntity<List<OrderDtoResponse>> findAllOrders() {
        List<OrderDtoResponse> orders = orderService.findAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get an order by ID", description = "Returns an order based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Order> findOrderById(@PathVariable UUID id) {
        Order order = orderService.findOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update an order", description = "Updates the data of an order based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully updated"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Order> updateOrder(@PathVariable UUID id,
                                             @RequestBody @Valid OrderDtoRequest orderDtoRequest) {
        Order updatedOrder = orderService.updateOrder(id, orderDtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an order", description = "Deletes an order based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
