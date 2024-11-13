package org.transcom.mappers;

import org.mapstruct.*;
import org.transcom.dto.TruckDtoRequest;
import org.transcom.entities.Truck;
import org.transcom.entities.TruckType;
import org.transcom.repositories.TruckTypeRepository;

import java.util.Optional;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TruckMapper {

    @Mapping(target = "truckStatus", constant = "UNAVAILABLE")
    @Mapping(target = "truckType", ignore = true)
    Truck toEntity(TruckDtoRequest truckDto);

    default Truck truckWithTruckTypeEntity(TruckDtoRequest truckDto, TruckTypeRepository truckTypeRepository) {
        Truck truck = toEntity(truckDto);
        Optional<TruckType> byShortName = truckTypeRepository.findByShortName(truckDto.getTruckTypeShortName());
        if (byShortName.isPresent()) {
            truck.setTruckType(byShortName.get());
            return truck;
        }
        return null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Truck partialUpdate(TruckDtoRequest truckDto, @MappingTarget Truck truck);
}