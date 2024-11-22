package org.transcom.services;

import org.transcom.dto.CompanyDtoRequest;
import org.transcom.entities.Company;

import java.util.List;

public interface CompanyService {

    Company saveCompany(CompanyDtoRequest companyDtoRequest);

    List<Company> findAllCompanies();

    Company findCompanyById(Long id);

    Company updateCompany(Long id, CompanyDtoRequest companyDtoRequest);

    boolean deleteCompany(Long id);

}
