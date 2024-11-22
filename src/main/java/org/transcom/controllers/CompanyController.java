package org.transcom.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.CompanyDtoRequest;
import org.transcom.entities.Company;
import org.transcom.services.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
@Validated
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Company> saveCompany(@RequestBody @Valid CompanyDtoRequest companyDtoRequest) {
        Company savedCompany = companyService.saveCompany(companyDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany);
    }

    @GetMapping
    public ResponseEntity<List<Company>> findAllCompanies() {
        List<Company> companies = companyService.findAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id) {
        Company company = companyService.findCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody @Valid CompanyDtoRequest companyDtoRequest) {
        Company updatedCompany = companyService.updateCompany(id, companyDtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (companyService.deleteCompany(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
