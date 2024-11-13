package org.transcom.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.TruckDtoRequest;
import org.transcom.entities.Truck;
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
    public ResponseEntity<Truck> createTruck(@RequestBody @Valid TruckDtoRequest truckDtoRequest) {
        Truck createdTruck = truckService.createTruck(truckDtoRequest);
        return ResponseEntity.ok(createdTruck);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Truck> getTruckById(@PathVariable UUID id) {
        Truck truck = truckService.findTruckById(id);
        return truck != null ? ResponseEntity.ok(truck) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        List<Truck> trucks = truckService.findAllTrucks();
        return ResponseEntity.ok(trucks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Truck> updateTruck(@PathVariable UUID id, @RequestBody TruckDtoRequest truckToUpdate) {
        Truck updatedTruck = truckService.updateTruck(id, truckToUpdate);
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
