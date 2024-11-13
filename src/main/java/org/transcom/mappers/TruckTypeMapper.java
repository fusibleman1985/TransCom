package org.transcom.mappers;


import org.mapstruct.*;
import org.transcom.dto.TruckTypeDtoRequest;
import org.transcom.entities.TruckType;

@Mapper(componentModel = "spring")
public interface TruckTypeMapper {

    @Mapping(target = "trucks", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "id", ignore = true)
    TruckType toTruckType(TruckTypeDtoRequest truckType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TruckType partialUpdate(TruckTypeDtoRequest truckTypeDto, @MappingTarget TruckType truckType);
}
