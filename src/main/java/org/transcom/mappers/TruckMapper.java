package org.transcom.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.transcom.dto.TruckDtoRequest;
import org.transcom.dto.TruckDtoResponse;
import org.transcom.entities.Truck;
import org.transcom.entities.TruckType;
import org.transcom.entities.User;
import org.transcom.exceptions.TruckTypeNotFoundException;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.repositories.TruckTypeRepository;
import org.transcom.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
@Mapper(componentModel = "spring", uses = {UserRepository.class})
public interface TruckMapper {

    @Mapping(target = "truckStatus", constant = "UNAVAILABLE")
    @Mapping(target = "truckType", ignore = true)
    Truck toEntity(TruckDtoRequest truckDto);

    default Truck truckWithTruckTypeAndUser(TruckDtoRequest truckDto, TruckTypeRepository truckTypeRepository, UserRepository userRepository) {
        Truck truck = toEntity(truckDto);

        TruckType truckType = truckTypeRepository.findByShortName(truckDto.getTruckTypeShortName())
                .orElseThrow(() -> new TruckTypeNotFoundException(ErrorMessages.TRUCK_TYPE_NOT_FOUND.getMessage()));
        truck.setTruckType(truckType);

        List<User> users = userRepository.findAllById(truckDto.getListUserId());
        if (users.size() != truckDto.getListUserId().size()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage());
        }
        truck.setUsers(users);

        return truck;
    }

    @Mapping(target = "truckTypeShortName", source = "truckType")
    @Mapping(target = "listUserId", source = "users")
    TruckDtoResponse toTruckDtoResponse(Truck truck);

    default List<UUID> mapUsersToIds(List<User> users) {
        return users.stream()
                .map(User::getId)
                .toList();
    }

    default List<User> mapIdsToUsers(List<UUID> usersIds, UserRepository userRepository) {
        return usersIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getMessage()))
                )
                .toList();
    }

    default String map(TruckType truckType) {
        return truckType.getShortName();
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
        truck.setUsers(mapIdsToUsers(truckDtoRequest.getListUserId(), userRepository));
    }
}