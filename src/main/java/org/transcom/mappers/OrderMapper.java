package org.transcom.mappers;

import org.mapstruct.*;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.dto.OrderDtoResponse;
import org.transcom.entities.Order;
import org.transcom.entities.User;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.repositories.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "orderStatus", constant = "CREATED")
    @Mapping(target = "users", source = "userIds", qualifiedByName = "mapUsersFromIds")
    @Mapping(target = "id", ignore = true)
    Order toEntity(OrderDtoRequest orderDtoRequest, @Context UserRepository userRepository);

    @Mapping(target = "userIds", source = "users", qualifiedByName = "mapIdsFromUsers")
    OrderDtoResponse toOrderDtoResponse(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", source = "userIds", qualifiedByName = "mapUsersFromIds")
    void updateEntityFromDto(OrderDtoRequest orderDtoRequest, @MappingTarget Order order, @Context UserRepository userRepository);

    @Named("mapUsersFromIds")
    default List<User> mapUsersFromIds(List<UUID> userIds, @Context UserRepository userRepository) {
        if (userIds == null || userIds.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage());
        }
        return userIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage())))
                .collect(Collectors.toList());
    }

    @Named("mapIdsFromUsers")
    default List<UUID> mapIdsFromUsers(List<User> users) {
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage());
        }
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    default OrderDtoResponse toOrderDtoResponseWithUserCheck(Order order) {
        if (order.getUsers() == null || order.getUsers().isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage());
        }
        return toOrderDtoResponse(order);
    }
}
