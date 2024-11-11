package org.transcom.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.transcom.dto.OrderDto;
import org.transcom.entities.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Named("toOrderDto")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "description", target = "description")
    OrderDto mapToDto(Order order);

    @IterableMapping(qualifiedByName = "toOrderDto")
    List<OrderDto> mapToListDto(List<Order> orders);

    @Named("toOrderEntity")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "description", target = "description")
    Order toEntity(OrderDto orderDto);
}
