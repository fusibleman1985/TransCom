package org.transcom.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.TruckTypeDto;
import org.transcom.entities.TruckType;
import org.transcom.services.TruckTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/truck-types")
public class TruckTypeController {

    @Autowired
    private TruckTypeService truckTypeService;

    @PostMapping
    public ResponseEntity<TruckType> createTruckType(@RequestBody TruckTypeDto truckTypeDto) {
        return ResponseEntity.ok(truckTypeService.createTruckType(truckTypeDto));
    }

    @GetMapping
    public ResponseEntity<List<TruckType>> getAllTruckTypes() {
        return ResponseEntity.ok(truckTypeService.getAllTruckTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckType> getTruckTypeById(@PathVariable Long id) {
        TruckType truckType = truckTypeService.getTruckTypeById(id);
        return truckType != null ? ResponseEntity.ok(truckType) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TruckType> updateTruckType(@PathVariable Long id, @RequestBody TruckType updatedTruckType) {
        TruckType truckType = truckTypeService.updateTruckType(id, updatedTruckType);
        return truckType != null ? ResponseEntity.ok(truckType) : ResponseEntity.notFound().build();
    }
}
