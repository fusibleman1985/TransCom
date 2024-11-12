package org.transcom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.TruckDto;
import org.transcom.services.TruckService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {

    @Autowired
    private TruckService truckService;

    @PostMapping
    public ResponseEntity<TruckDto> createTruck(@RequestBody TruckDto truckDto) {
        TruckDto createdTruck = truckService.createTruck(truckDto);
        return ResponseEntity.ok(createdTruck);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckDto> getTruckById(@PathVariable UUID id) {
        TruckDto truck = truckService.findTruckById(id);
        return truck != null ? ResponseEntity.ok(truck) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TruckDto>> getAllTrucks() {
        List<TruckDto> trucks = truckService.findAllTrucks();
        return ResponseEntity.ok(trucks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TruckDto> updateTruck(@PathVariable UUID id, @RequestBody TruckDto truckDto) {
        TruckDto updatedTruck = truckService.updateTruck(id, truckDto);
        return updatedTruck != null ? ResponseEntity.ok(updatedTruck) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable UUID id) {
        truckService.deleteTruck(id);
        return ResponseEntity.noContent().build();
    }
}
