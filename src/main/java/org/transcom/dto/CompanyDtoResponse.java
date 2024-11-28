package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.transcom.entities.enums.ClientStatus;
import org.transcom.entities.enums.CompanyRole;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDtoResponse that = (CompanyDtoResponse) o;
        return Objects.equals(companyName, that.companyName) && companyRole == that.companyRole && Objects.equals(licenseId, that.licenseId) && Objects.equals(rating, that.rating) && companyStatus == that.companyStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, companyRole, licenseId, rating, companyStatus);
    }

    public CompanyDtoResponse() {
        this.rating = 50;
    }
}