package org.transcom.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.CompanyDtoRequest;
import org.transcom.dto.CompanyDtoResponse;
import org.transcom.services.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
@Validated
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<CompanyDtoResponse> createCompany(@RequestBody @Valid CompanyDtoRequest companyDtoRequest) {
        CompanyDtoResponse savedCompany = companyService.saveCompany(companyDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CompanyDtoResponse>> getAllCompanies() {
        List<CompanyDtoResponse> companies = companyService.findAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CompanyDtoResponse> getCompanyById(@PathVariable Long id) {
        CompanyDtoResponse companyById = companyService.findCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(companyById);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyDtoResponse> updateCompany(@PathVariable Long id, @RequestBody @Valid CompanyDtoRequest companyDtoRequest) {
        CompanyDtoResponse updatedCompany = companyService.updateCompany(id, companyDtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (companyService.deleteCompany(id)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
