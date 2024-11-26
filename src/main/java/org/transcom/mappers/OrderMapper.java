package org.transcom.mappers;

import org.mapstruct.*;
import org.transcom.dto.OrderDtoRequest;
import org.transcom.dto.OrderDtoResponse;
import org.transcom.entities.Order;
import org.transcom.entities.User;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.repositories.FavoriteRepository;
import org.transcom.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderStatus", constant = "CREATED")
    @Mapping(target = "users", ignore = true)
    Order toOrderDtoResponse(OrderDtoRequest orderDtoRequest);

    default Order toOrderDtoResponse(OrderDtoRequest orderDtoRequest, UserRepository userRepository) {
        Order order = toOrderDtoResponse(orderDtoRequest);
        order.setUsers(mapUUIDsToUsers(orderDtoRequest.getUserIds(), userRepository));
        return order;
    }

    @Mapping(target = "favoriteByUserId", ignore = true)
    OrderDtoResponse toOrderDtoResponse(Order order);

    default OrderDtoResponse toOrderDtoResponse(Order order, @Context FavoriteRepository favoriteRepository) {
        OrderDtoResponse orderDtoResponse = toOrderDtoResponse(order);

        orderDtoResponse.setFavoriteByUserId(favoriteRepository.findUsersWhoFavoritedOrders(order.getId()));

        return orderDtoResponse;
    }

    default void updatedEntityFromDto(OrderDtoRequest orderDtoRequest, @MappingTarget Order order, UserRepository userRepository) {
        order.setWeight(orderDtoRequest.getWeight());
        order.setPrice(orderDtoRequest.getPrice());
        if (orderDtoRequest.getDescription() != null && !orderDtoRequest.getDescription().isEmpty()) {
            order.setDescription(orderDtoRequest.getDescription());
        }
        if (orderDtoRequest.getOrderStatus() != null) {
            order.setOrderStatus(orderDtoRequest.getOrderStatus());
        }
        List<UUID> userIds = Optional.ofNullable(orderDtoRequest.getUserIds())
                .orElse(new ArrayList<>());
        order.setUsers(mapUUIDsToUsers(userIds, userRepository));
    }

    default List<User> mapUUIDsToUsers(List<UUID> usersIds, UserRepository userRepository) {
        return usersIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("{error.user_not_found}"))
                )
                .toList();
    }
}