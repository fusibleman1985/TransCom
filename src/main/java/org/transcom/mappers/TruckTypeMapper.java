package org.transcom.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.transcom.dto.TruckTypeDto;
import org.transcom.entities.TruckType;

@Mapper(componentModel = "spring")
public interface TruckTypeMapper {

    @Mapping(target = "trucks", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "id", ignore = true)
    TruckType toTruckType(TruckTypeDto truckType);

}
