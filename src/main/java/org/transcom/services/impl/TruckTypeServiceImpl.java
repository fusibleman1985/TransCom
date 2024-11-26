package org.transcom.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.TruckTypeDtoRequest;
import org.transcom.entities.TruckType;
import org.transcom.exceptions.TruckTypeDeleteException;
import org.transcom.exceptions.TruckTypeNotFoundException;
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
        return truckTypeRepository.save(truckTypeMapper.toTruckType(truckTypeDtoRequest));
    }

    @Override
    public List<TruckType> findAllTruckTypes() {
        return truckTypeRepository.findAll();
    }

    @Override
    public TruckType findTruckType(Long id) {
        return truckTypeRepository.findById(id)
                .orElseThrow(() -> new TruckTypeNotFoundException("{error.truck_type_not_found}"));
    }

    @Override
    @Transactional
    public TruckType updateTruckType(Long id, TruckTypeDtoRequest truckTypeDtoRequest) {
        TruckType truckTypeById = findTruckType(id);
        truckTypeMapper.updateTruckTypeFromDto(truckTypeDtoRequest, truckTypeById);
        return truckTypeRepository.save(truckTypeById);
    }

    @Override
    @Transactional
    public boolean deleteTruckType(Long id) {
        TruckType truckTypeById = findTruckType(id);

        if (truckRepository.existsByTruckTypeShortName(truckTypeById.getShortName())) {
            throw new TruckTypeDeleteException("{error.error_deleting_truck_type}");
        }

        truckTypeRepository.delete(truckTypeById);
        return true;
    }
}
