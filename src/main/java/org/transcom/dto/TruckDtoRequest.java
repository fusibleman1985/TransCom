package org.transcom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transcom.entities.enums.TruckStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TruckDtoRequest {

    @Min(message = "Length must be greater than zero", value = 1)
    private int length;

    @Min(message = "Weight must be greater than one", value = 1)
    private int weight;

    @Min(message = "Capacity must be greater than one", value = 1)
    private Double capacity;

    @NotNull
    private TruckStatus truckStatus;

    @NotNull
    @NotEmpty
    private String location;

    @NotNull
    private String truckTypeShortName;
}
