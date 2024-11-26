package org.transcom.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.CompanyDtoRequest;
import org.transcom.dto.CompanyDtoResponse;
import org.transcom.entities.Company;
import org.transcom.entities.enums.ClientStatus;
import org.transcom.exceptions.CompanyNotFoundException;
import org.transcom.mappers.CompanyMapper;
import org.transcom.repositories.CompanyRepository;
import org.transcom.repositories.UserRepository;
import org.transcom.services.CompanyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDtoResponse saveCompany(CompanyDtoRequest companyDtoRequest) {
        Company companyToSave = companyMapper.toEntity(companyDtoRequest, userRepository);
        Company savedCompany = companyRepository.save(companyToSave);
        return companyMapper.toDtoResponse(savedCompany);
    }

    @Override
    public List<CompanyDtoResponse> findAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(companyMapper::toDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDtoResponse findCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException("{error.company_not_found}"));
        return companyMapper.toDtoResponse(company);
    }

    @Override
    @Transactional
    public CompanyDtoResponse updateCompany(Long id, CompanyDtoRequest companyDtoRequest) {
        Company companyToUpdate = companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException("{error.company_not_found}"));
        companyMapper.updateCompanyFromDto(companyDtoRequest, companyToUpdate, userRepository);
        return companyMapper.toDtoResponse(companyToUpdate);
    }

    @Override
    @Transactional
    public boolean deleteCompany(Long id) {
        Company companyById = companyRepository.findById(id).orElseThrow(() ->
                new CompanyNotFoundException("{error.company_not_found}"));
        companyById.setCompanyStatus(ClientStatus.DELETED);
        companyRepository.save(companyById);
        return true;
    }
}
