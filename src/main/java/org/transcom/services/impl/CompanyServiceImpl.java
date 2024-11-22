package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.CompanyDtoRequest;
import org.transcom.entities.Company;
import org.transcom.entities.enums.UserStatus;
import org.transcom.exceptions.CompanyNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.mappers.CompanyMapper;
import org.transcom.repositories.CompanyRepository;
import org.transcom.repositories.UserRepository;
import org.transcom.services.CompanyService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Company saveCompany(CompanyDtoRequest companyDtoRequest) {
        Company company = companyMapper.toEntity(companyDtoRequest, userRepository);
        return companyRepository.save(company);
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company findCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException(ErrorMessages.COMPANY_NOT_FOUND.getMessage()));
    }

    @Override
    public Company updateCompany(Long id, CompanyDtoRequest companyDtoRequest) {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException("Company not found with id: " + id));
        companyMapper.updateCompanyFromDto(companyDtoRequest, company, userRepository);
        return companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        Company companyById = companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException(ErrorMessages.COMPANY_NOT_FOUND.getMessage()));
        if (companyById != null) {
            companyById.setCompanyStatus(UserStatus.DELETED); //---------------------------------
            companyRepository.save(companyById);
            return true;
        } else {
            return false;
        }
    }
}
