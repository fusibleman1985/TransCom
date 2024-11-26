package org.transcom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TruckTypeDtoRequest {

    @NotBlank(message = "ShortName must not be empty")
    private String shortName;

    @NotBlank(message = "FullName must not be empty")
    private String fullName;

    @Min(value = 1, message = "LengthMeters must be greater than 1 meter")
    private double lengthMeters;

    @Min(value = 1, message = "CapacityCubicUnits must be greater then 1 meter")
    private double capacityCubicUnits;

    private String imageUrl;
}