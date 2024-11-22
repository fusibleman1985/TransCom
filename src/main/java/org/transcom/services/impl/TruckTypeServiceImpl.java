package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.TruckTypeDtoRequest;
import org.transcom.entities.TruckType;
import org.transcom.exceptions.TruckTypeDeleteException;
import org.transcom.exceptions.TruckTypeNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.mappers.TruckTypeMapper;
import org.transcom.repositories.TruckRepository;
import org.transcom.repositories.TruckTypeRepository;
import org.transcom.services.TruckTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TruckTypeServiceImpl implements TruckTypeService {

    private final TruckTypeRepository truckTypeRepository;
    private final TruckRepository truckRepository;
    private final TruckTypeMapper truckTypeMapper;

    @Override
    public TruckType createTruckType(TruckTypeDtoRequest truckTypeDtoRequest) {
        TruckType truckType = truckTypeMapper.toTruckType(truckTypeDtoRequest);
        return truckTypeRepository.save(truckType);
    }

    @Override
    public TruckType findTruckType(Long id) {
        TruckType truckTypeById = truckTypeRepository.findById(id).orElseThrow(() ->
                new TruckTypeNotFoundException(ErrorMessages.TRUCK_TYPE_NOT_FOUND.getMessage()));
        return truckTypeById;
    }

    @Override
    public List<TruckType> findAllTruckTypes() {
        return truckTypeRepository.findAll();
    }

    @Override
    public TruckType updateTruckType(Long id, TruckTypeDtoRequest truckTypeDtoRequest) {
        TruckType truckTypeById = findTruckType(id);
        if (truckTypeById != null) {
            truckTypeMapper.partialUpdate(truckTypeDtoRequest, truckTypeById);
            return truckTypeRepository.save(truckTypeById);
        }
        return null;
    }

    @Override
    public boolean deleteTruckType(Long id) {
        TruckType truckTypeById = findTruckType(id);
        if (truckTypeById != null) {
            if (truckRepository.existsByTruckTypeShortName(truckTypeById.getShortName())) {
                throw new TruckTypeDeleteException(ErrorMessages.ERROR_DELETING_TRUCK_TYPE.getMessage());
            }
            truckTypeRepository.delete(truckTypeById);
            return true;
        }
        return false;
    }
}
