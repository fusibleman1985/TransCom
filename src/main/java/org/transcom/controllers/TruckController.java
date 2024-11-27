package org.transcom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.TruckDtoRequest;
import org.transcom.dto.TruckDtoResponse;
import org.transcom.services.TruckService;
import org.transcom.validation.annotations.ValidUUID;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class TruckController {

    @Autowired
    private TruckService truckService;

    @PostMapping("/create")
    @Operation(summary = "Create a new truck", description = "Creates a new truck based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<TruckDtoResponse> createTruck(@RequestBody @Valid TruckDtoRequest truckDtoRequest) {
        TruckDtoResponse createdTruck = truckService.createTruck(truckDtoRequest);
        return ResponseEntity.ok(createdTruck);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all trucks", description = "Returns a list of all trucks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trucks successfully retrieved")
    })
    public ResponseEntity<List<TruckDtoResponse>> getAllTrucks() {
        List<TruckDtoResponse> trucks = truckService.findAllTrucks();
        return ResponseEntity.ok(trucks);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a truck by ID", description = "Returns a truck based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Truck not found")
    })
    public ResponseEntity<TruckDtoResponse> getTruckById(@PathVariable @ValidUUID UUID id) {
        TruckDtoResponse truckDtoResponse = truckService.findTruckById(id);
        return truckDtoResponse != null ? ResponseEntity.ok(truckDtoResponse) : ResponseEntity.notFound().build();
    }

    @GetMapping("/get/shortName")
    @Operation(summary = "Get trucks by truck type short name", description = "Returns a list of trucks based on the " +
            "provided truck type short name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trucks successfully retrieved")
    })
    public ResponseEntity<List<TruckDtoResponse>> getTrucksByTruckTypeShortName(@RequestParam String shortName) {
        List<TruckDtoResponse> trucks = truckService.findTrucksByTruckTypeShortName(shortName);
        return ResponseEntity.ok(trucks);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a truck", description = "Updates the data of a truck based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck successfully updated"),
            @ApiResponse(responseCode = "404", description = "Truck not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<TruckDtoResponse> updateTruck(@PathVariable @ValidUUID UUID id,
                                                        @RequestBody @Valid TruckDtoRequest truckToUpdate) {
        TruckDtoResponse updatedTruck = truckService.updateTruck(id, truckToUpdate);
        return updatedTruck != null ? ResponseEntity.ok(updatedTruck) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a truck", description = "Deletes a truck based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Truck successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Truck not found")
    })
    public ResponseEntity<Void> deleteTruck(@PathVariable @ValidUUID UUID id) {
        if (truckService.deleteTruck(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
