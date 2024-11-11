package org.transcom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.transcom.entities.TruckType;
import org.transcom.repositories.TruckTypeRepository;

import java.util.List;

@Service
public class TruckTypeService {

    @Autowired
    private TruckTypeRepository truckTypeRepository;

    public TruckType createTruckType(TruckType truckType) {
        return truckTypeRepository.save(truckType);
    }

    public List<TruckType> getAllTruckTypes() {
        return truckTypeRepository.findAll();
    }

    public TruckType getTruckTypeById(Long id) {
        return truckTypeRepository.findById(id).orElse(null);
    }

    public TruckType updateTruckType(Long id, TruckType updatedTruckType) {
        TruckType truckType = getTruckTypeById(id);
        if (truckType != null) {
            truckType.setShortName(updatedTruckType.getShortName());
            truckType.setFullName(updatedTruckType.getFullName());
            truckType.setLengthMeters(updatedTruckType.getLengthMeters());
            truckType.setCapacityCubicUnits(updatedTruckType.getCapacityCubicUnits());
            truckType.setImageUrl(updatedTruckType.getImageUrl());
            return truckTypeRepository.save(truckType);
        }
        return null;
    }
}
