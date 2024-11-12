package org.transcom.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

//    OrderDtoRequest mapToDto(Order order);
    @Mapping(target = "orderStatus", constant = "CREATED")
    Order toEntity(OrderDtoRequest orderDtoRequest);

    Order updatedEntityFromDto(OrderDtoRequest orderDtoRequest, @MappingTarget Order order);
}