package org.transcom.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.transcom.dto.TruckTypeDtoRequest;
import org.transcom.entities.TruckType;

@Mapper(componentModel = "spring")
public interface TruckTypeMapper {

    @Mapping(target = "trucks", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "id", ignore = true)
    TruckType toTruckType(TruckTypeDtoRequest truckType);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trucks", ignore = true)
    default void updateTruckTypeFromDto(TruckTypeDtoRequest truckTypeDto, @MappingTarget TruckType truckType) {
        if (truckTypeDto.getShortName() != null && !truckTypeDto.getShortName().isEmpty()) {
            truckType.setShortName(truckTypeDto.getShortName());
        }
        if (truckTypeDto.getFullName() != null && !truckTypeDto.getFullName().isEmpty()) {
            truckType.setFullName(truckTypeDto.getFullName());
        }
        truckType.setLengthMeters(truckTypeDto.getLengthMeters());
        truckType.setCapacityCubicUnits(truckTypeDto.getCapacityCubicUnits());
        if (truckTypeDto.getImageUrl() != null && !truckTypeDto.getImageUrl().isEmpty()) {
            truckType.setImageUrl(truckTypeDto.getImageUrl());
        }
    }
}