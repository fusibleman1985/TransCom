package org.transcom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Company Controller", description = "Controller for managing companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create")
    @Operation(summary = "Create a new company", description = "Creates a new company based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<CompanyDtoResponse> createCompany(@RequestBody @Valid CompanyDtoRequest companyDtoRequest) {
        CompanyDtoResponse savedCompany = companyService.saveCompany(companyDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all companies", description = "Returns a list of all companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Companies successfully found")
    })
    public ResponseEntity<List<CompanyDtoResponse>> getAllCompanies() {
        List<CompanyDtoResponse> companies = companyService.findAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a company by ID", description = "Returns a company based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public ResponseEntity<CompanyDtoResponse> getCompanyById(@PathVariable Long id) {
        CompanyDtoResponse companyById = companyService.findCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(companyById);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a company", description = "Updates the data of a company based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company successfully updated"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<CompanyDtoResponse> updateCompany(@PathVariable Long id,
                                                            @RequestBody @Valid CompanyDtoRequest companyDtoRequest) {
        CompanyDtoResponse updatedCompany = companyService.updateCompany(id, companyDtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCompany);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a company", description = "Deletes a company based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (companyService.deleteCompany(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
