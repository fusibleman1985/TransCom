package org.transcom.dto;

import lombok.*;
import org.transcom.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private int weight;
    private BigDecimal price;
    private String description;
    private OrderStatus orderStatus;
//    private UserDto user;
}
