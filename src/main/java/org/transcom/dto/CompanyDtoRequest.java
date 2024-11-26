package org.transcom.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.transcom.entities.enums.ClientStatus;
import org.transcom.entities.enums.CompanyRole;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CompanyDtoRequest {

    @NotNull
    @NotBlank(message = "CompanyName cannot be blank")
    private String companyName;

    @NotNull
    private CompanyRole companyRole;

    @NotNull
    @NotBlank(message = "LicenseId cannot be blank")
    private String licenseId;

    @Builder.Default
    @Min(value = 0)
    @Max(value = 100)
    private Integer rating = 90;

    @NotNull
    private ClientStatus companyStatus;

    private List<UUID> userIds;

}