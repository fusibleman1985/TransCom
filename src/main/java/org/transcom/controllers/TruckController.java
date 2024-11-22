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

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/trucks")
@RequiredArgsConstructor
public class TruckController {

    @Autowired
    private TruckService truckService;

    @PostMapping
    public ResponseEntity<TruckDtoResponse> createTruck(@RequestBody @Valid TruckDtoRequest truckDtoRequest) {
        TruckDtoResponse createdTruck = truckService.createTruck(truckDtoRequest);
        return ResponseEntity.ok(createdTruck);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckDtoResponse> getTruckById(@PathVariable UUID id) {
        TruckDtoResponse truckDtoResponse = truckService.findTruckById(id);
        return truckDtoResponse != null ? ResponseEntity.ok(truckDtoResponse) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TruckDtoResponse>> getAllTrucks() {
        List<TruckDtoResponse> trucks = truckService.findAllTrucks();
        return ResponseEntity.ok(trucks);
    }

    @GetMapping("/shortName")
    public ResponseEntity<List<TruckDtoResponse>> findTrucksByTruckTypeShortName(@RequestParam String shortName) {
        List<TruckDtoResponse> trucks = truckService.findTrucksByTruckTypeShortName(shortName);
        return ResponseEntity.ok(trucks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TruckDtoResponse> updateTruck(@PathVariable UUID id, @RequestBody @Valid TruckDtoRequest truckToUpdate) {
        TruckDtoResponse updatedTruck = truckService.updateTruck(id, truckToUpdate);
        return updatedTruck != null ? ResponseEntity.ok(updatedTruck) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable UUID id) {
        if (truckService.deleteTruck(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
