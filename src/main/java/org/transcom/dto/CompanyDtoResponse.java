package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.transcom.entities.enums.ClientStatus;
import org.transcom.entities.enums.CompanyRole;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CompanyDtoResponse {

    private String companyName;
    private CompanyRole companyRole;
    private String licenseId;
    private Integer rating;
    private ClientStatus companyStatus;
    private List<String> usersFullNameAndId;

    public CompanyDtoResponse() {
        this.rating = 50;
    }
}