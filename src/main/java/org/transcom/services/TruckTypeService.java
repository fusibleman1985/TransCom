package org.transcom.services;

import org.transcom.dto.TruckTypeDtoRequest;
import org.transcom.entities.TruckType;

import java.util.List;

public interface TruckTypeService {

    TruckType createTruckType(TruckTypeDtoRequest truckType);

    TruckType findTruckType(Long id);

    List<TruckType> findAllTruckTypes();

    TruckType updateTruckType(Long id, TruckTypeDtoRequest truckType);

    boolean deleteTruckType(Long id);
}
