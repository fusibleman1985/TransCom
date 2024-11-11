package org.transcom.services;

import org.transcom.dto.TruckTypeDto;
import org.transcom.entities.TruckType;

import java.util.List;

public interface TruckTypeService {

    TruckType createTruckType(TruckTypeDto truckType);

    TruckType findTruckType(Long id);

    List<TruckType> findAllTruckTypes();

    TruckType updateTruckType(Long id, TruckTypeDto truckType);

    void deleteTruckType(Long id);
}
