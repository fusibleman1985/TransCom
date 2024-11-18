package org.transcom.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.entities.Order;
import org.transcom.entities.User;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderStatus", constant = "CREATED")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    Order toEntity(OrderDtoRequest orderDtoRequest, User user);

    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    void updatedEntityFromDto(OrderDtoRequest orderDtoRequest, @MappingTarget Order order, User user);
}