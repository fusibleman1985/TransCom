package org.transcom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDtoRequest {

    @Min(value = 1, message = "Weight must be greater than zero")
    private Integer weight;

    private Double capacityCubicUnits;

    private String description;

    @Min(value = 1, message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull
    private List<UUID> userIds;

}