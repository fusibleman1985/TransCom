package org.transcom.controllers;

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
    public ResponseEntity<TruckDtoResponse> createTruck(@RequestBody @Valid TruckDtoRequest truckDtoRequest) {
        TruckDtoResponse createdTruck = truckService.createTruck(truckDtoRequest);
        return ResponseEntity.ok(createdTruck);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TruckDtoResponse>> getAllTrucks() {
        List<TruckDtoResponse> trucks = truckService.findAllTrucks();
        return ResponseEntity.ok(trucks);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TruckDtoResponse> getTruckById(@PathVariable @ValidUUID UUID id) {
        TruckDtoResponse truckDtoResponse = truckService.findTruckById(id);
        return truckDtoResponse != null ? ResponseEntity.ok(truckDtoResponse) : ResponseEntity.notFound().build();
    }

    @GetMapping("/get/shortName")
    public ResponseEntity<List<TruckDtoResponse>> getTrucksByTruckTypeShortName(@RequestParam String shortName) {
        List<TruckDtoResponse> trucks = truckService.findTrucksByTruckTypeShortName(shortName);
        return ResponseEntity.ok(trucks);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TruckDtoResponse> updateTruck(@PathVariable @ValidUUID UUID id, @RequestBody @Valid TruckDtoRequest truckToUpdate) {
        TruckDtoResponse updatedTruck = truckService.updateTruck(id, truckToUpdate);
        return updatedTruck != null ? ResponseEntity.ok(updatedTruck) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable @ValidUUID UUID id) {
        if (truckService.deleteTruck(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
