package org.transcom.services;

import org.transcom.dto.CompanyDtoRequest;
import org.transcom.dto.CompanyDtoResponse;

import java.util.List;

public interface CompanyService {

    CompanyDtoResponse saveCompany(CompanyDtoRequest companyDtoRequest);

    List<CompanyDtoResponse> findAllCompanies();

    CompanyDtoResponse findCompanyById(Long id);

    CompanyDtoResponse updateCompany(Long id, CompanyDtoRequest companyDtoRequest);

    boolean deleteCompany(Long id);

}
