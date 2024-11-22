package org.transcom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.transcom.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDtoRequest {

    @NotNull
    private UUID userId;

    @Min(value = 1, message = "Weight must be greater than zero")
    private Integer weight;

    @Min(value = 1, message = "Price must be greater than zero")
    private BigDecimal price;

    private String description;

    private OrderStatus orderStatus;
}