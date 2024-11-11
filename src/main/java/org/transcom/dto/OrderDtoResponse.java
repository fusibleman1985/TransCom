package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transcom.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDtoResponse {
    private UUID id;
    private int weight;
    private BigDecimal price;
    private String description;
    private OrderStatus status;
    private UserDto user;
}
