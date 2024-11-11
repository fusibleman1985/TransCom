package org.transcom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "must not be empty")
    private String shortName;

    @NotBlank(message = "must not be empty")
    private String fullName;

    @Min(value = 1, message = "must be greater than 1 meter")
    private double lengthMeters;

    @Min(value = 1, message = "must be greater then 1 meter")
    private double capacityCubicUnits;

    private String imageUrl;
}