package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link org.transcom.entities.TruckType}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TruckTypeDto {
    private String shortName;
    private String fullName;
    private double lengthMeters;
    private double capacityCubicUnits;
    private String imageUrl;
}