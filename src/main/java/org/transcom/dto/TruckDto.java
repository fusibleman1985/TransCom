package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TruckDto {
    private UUID id;
    private int feet;
    private int weight;
    private Double capacity;
    private String truckStatus;
    private String location;
    private UUID truckTypeId;
}
