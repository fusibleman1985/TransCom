package org.transcom.mappers;

import org.mapstruct.*;
import org.transcom.dto.TruckDtoRequest;
import org.transcom.dto.TruckDtoResponse;
import org.transcom.entities.Role;
import org.transcom.entities.Truck;
import org.transcom.entities.TruckType;
import org.transcom.entities.User;
import org.transcom.entities.enums.StringConstants;
import org.transcom.exceptions.TruckTypeNotFoundException;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.repositories.FavoriteRepository;
import org.transcom.repositories.TruckTypeRepository;
import org.transcom.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring", uses = {UserRepository.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FavoriteRepository.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TruckMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "truckStatus", constant = "UNAVAILABLE")
    @Mapping(target = "truckType", ignore = true)
    @Mapping(target = "activeUsers", ignore = true)
    Truck toEntity(TruckDtoRequest truckDto);

    default Truck toCreatedTruck(TruckDtoRequest truckDtoRequest, TruckTypeRepository truckTypeRepository, UserRepository userRepository) {
        Truck truck = toEntity(truckDtoRequest);
        TruckType truckType = truckTypeRepository.findByShortName(truckDtoRequest.getTruckTypeShortName())
                .orElseThrow(() -> new TruckTypeNotFoundException("{error.truck_type_not_found}"));
        truck.setTruckType(truckType);

        List<UUID> activeUserIds = Optional.ofNullable(truckDtoRequest.getActiveUserIDs())
                .orElse(Collections.emptyList());

        List<User> users = userRepository.findAllById(activeUserIds);

        if (!users.isEmpty() && users.size() != truckDtoRequest.getActiveUserIDs().size()) {
            throw new UserNotFoundException("{error.user_not_found}");
        }

        truck.setActiveUsers(users);
        return truck;
    }

    @Mapping(target = "activeUsers", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "favoriteByUserId", expression = "java(new java.util.ArrayList<>())")
    TruckDtoResponse toTruckDtoResponse(Truck truck);

    default TruckDtoResponse toTruckDtoResponse(Truck truck, FavoriteRepository favoriteRepository) {
        TruckDtoResponse truckDtoResponse = toTruckDtoResponse(truck);

        truckDtoResponse.setTruckTypeShortName(truck.getTruckType().getShortName());
        truckDtoResponse.setActiveUsers(mapUsersToStringActiveUsers(truck.getActiveUsers()));
        truckDtoResponse.setFavoriteByUserId(favoriteRepository.findUsersWhoFavoritedTruck(truck.getId()));

        return truckDtoResponse;
    }

    default List<String> mapUsersToStringActiveUsers(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }
        return users.stream()
                .filter(Objects::nonNull)
                .map(user -> String.format("%s (%s), %s",
                        user.getFullName() != null ? user.getFullName() : StringConstants.UNKNOWN_USER.getValue(),
                        user.getId() != null ? user.getId().toString() : StringConstants.UNKNOWN_ID.getValue(),
                        user.getUserRoles().stream()
                                .map(Role::getRoleName)
                                .collect(Collectors.joining(", "))))
                .toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", source = "listUserId")
    default void updateTruckFromDtoRequest(TruckDtoRequest truckDtoRequest, @MappingTarget Truck truck, TruckTypeRepository truckTypeRepository, UserRepository userRepository) {
        truck.setLength(truckDtoRequest.getLength());
        truck.setWeight(truckDtoRequest.getWeight());
        truck.setCapacity(truckDtoRequest.getCapacity());
        if (truckDtoRequest.getTruckStatus() != null) {
            truck.setTruckStatus(truckDtoRequest.getTruckStatus());
        }
        if (truckDtoRequest.getTruckTypeShortName() != null && !truckDtoRequest.getTruckTypeShortName().isEmpty()) {
            TruckType updateTruckType = truckTypeRepository.findByShortName(truckDtoRequest.getTruckTypeShortName()).orElse(null);
            if (updateTruckType != null) {
                truck.setTruckType(updateTruckType);
            }
        }
        truck.setActiveUsers(mapIdsToUsers(truckDtoRequest.getActiveUserIDs(), userRepository));
    }

    default List<User> mapIdsToUsers(List<UUID> usersIds, UserRepository userRepository) {
        return usersIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("{error.user_not_found}"))
                )
                .toList();
    }
}