package org.transcom.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.TruckTypeDto;
import org.transcom.entities.TruckType;
import org.transcom.services.impl.TruckTypeServiceImpl;

import java.util.List;

@Validated
@RestController
@RequestMapping("/truck-types")
public class TruckTypeController {

    @Autowired
    private TruckTypeServiceImpl truckTypeService;

    @PostMapping
    public ResponseEntity<TruckType> createTruckType(@RequestBody @Valid TruckTypeDto truckTypeDto) {
        return ResponseEntity.ok(truckTypeService.createTruckType(truckTypeDto));
    }

    @GetMapping
    public ResponseEntity<List<TruckType>> getAllTruckTypes() {
        return ResponseEntity.ok(truckTypeService.findAllTruckTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckType> getTruckTypeById(@PathVariable Long id) {
        TruckType truckType = truckTypeService.findTruckType(id);
        return truckType != null ? ResponseEntity.ok(truckType) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TruckType> updateTruckType(@PathVariable Long id, @RequestBody @Valid TruckTypeDto truckTypeToUpdate) {
        TruckType truckType = truckTypeService.updateTruckType(id, truckTypeToUpdate);
        return truckType != null ? ResponseEntity.ok(truckType) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruckType(@PathVariable Long id) {
        truckTypeService.deleteTruckType(id);
        return ResponseEntity.noContent().build();
    }
}
