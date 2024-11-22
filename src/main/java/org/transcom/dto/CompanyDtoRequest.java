package org.transcom.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.transcom.entities.enums.UserStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CompanyDtoRequest {

    @NotNull
    @Min(value = 1, message = "CompanyName must be greater than zero")
    private String companyName;

    @NotNull
    private String companyRole;

    @NotNull
    @NotBlank(message = "LicenseId cannot be blank")
    private String licenseId;

    @Min(value = 0)
    @Max(value = 100)
    private Integer rating;

    @NotNull
    private UserStatus companyStatus;

    private List<UUID> userIds;

    public CompanyDtoRequest() {
        this.rating = 50;
    }
}