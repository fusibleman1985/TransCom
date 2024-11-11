package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.TruckTypeDto;
import org.transcom.entities.TruckType;
import org.transcom.mappers.TruckTypeMapper;
import org.transcom.repositories.TruckTypeRepository;
import org.transcom.services.TruckTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TruckTypeServiceImpl implements TruckTypeService {

    private final TruckTypeRepository truckTypeRepository;
    private final TruckTypeMapper truckTypeMapper;

    @Override
    public TruckType createTruckType(TruckTypeDto truckTypeDto) {
        TruckType truckType = truckTypeMapper.toTruckType(truckTypeDto);
        return truckTypeRepository.save(truckType);
    }


    @Override
    public TruckType findTruckType(Long id) {
        return truckTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<TruckType> findAllTruckTypes() {
        return truckTypeRepository.findAll();
    }

    @Override
    public TruckType updateTruckType(Long id, TruckTypeDto truckTypeDto) {
        TruckType truckTypeById = findTruckType(id);
        if (truckTypeById != null) {
            truckTypeById.setShortName(truckTypeDto.getShortName());
            truckTypeById.setFullName(truckTypeDto.getFullName());
            truckTypeById.setLengthMeters(truckTypeDto.getLengthMeters());
            truckTypeById.setCapacityCubicUnits(truckTypeDto.getCapacityCubicUnits());
            truckTypeById.setImageUrl(truckTypeDto.getImageUrl());
            return truckTypeRepository.save(truckTypeById);
        }
        return null;
    }

    @Override
    public void deleteTruckType(Long id) {
        TruckType truckTypeById = findTruckType(id);
        if (truckTypeById != null) {
//            truckTypeById.setTrucks(null);
//            truckTypeRepository.save(truckTypeById);
            truckTypeRepository.delete(truckTypeById);
        }
    }

}
