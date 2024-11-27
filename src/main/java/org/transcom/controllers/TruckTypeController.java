package org.transcom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.TruckTypeDtoRequest;
import org.transcom.entities.TruckType;
import org.transcom.services.impl.TruckTypeServiceImpl;

import java.util.List;

@Validated
@RestController
@RequestMapping("/truck-types")
@Tag(name = "Truck Type Controller", description = "Controller for managing truck types")
public class TruckTypeController {

    @Autowired
    private TruckTypeServiceImpl truckTypeService;

    @PostMapping("/create")
    @Operation(summary = "Create a new truck type", description = "Creates a new truck type based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck type successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<TruckType> createTruckType(@RequestBody @Valid TruckTypeDtoRequest truckTypeDtoRequest) {
        return ResponseEntity.ok(truckTypeService.createTruckType(truckTypeDtoRequest));
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all truck types", description = "Returns a list of all truck types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck types successfully retrieved")
    })
    public ResponseEntity<List<TruckType>> getAllTruckTypes() {
        return ResponseEntity.ok(truckTypeService.findAllTruckTypes());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a truck type by ID", description = "Returns a truck type based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck type successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Truck type not found")
    })
    public ResponseEntity<TruckType> getTruckTypeById(@PathVariable Long id) {
        TruckType truckType = truckTypeService.findTruckType(id);
        return truckType != null ? ResponseEntity.ok(truckType)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a truck type", description = "Updates the data of a truck type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck type successfully updated"),
            @ApiResponse(responseCode = "404", description = "Truck type not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<TruckType> updateTruckType(@PathVariable Long id,
                                                     @RequestBody @Valid TruckTypeDtoRequest truckTypeToUpdate) {
        TruckType truckType = truckTypeService.updateTruckType(id, truckTypeToUpdate);
        return truckType != null ? ResponseEntity.ok(truckType) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a truck type", description = "Deletes a truck type based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck type successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Truck type not found")
    })
    public ResponseEntity<Void> deleteTruckType(@PathVariable Long id) {
        if (truckTypeService.deleteTruckType(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
