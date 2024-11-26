package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoResponse {
    private UUID id;
    private Integer weight;
    private Double capacityCubicUnits;
    private String description;
    private BigDecimal price;
    private List<UUID> userIds;
}
