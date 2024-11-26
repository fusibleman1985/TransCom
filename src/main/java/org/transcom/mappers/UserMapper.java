package org.transcom.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.*;
import org.transcom.exceptions.CompanyNotFoundException;
import org.transcom.exceptions.OrderNotFoundException;
import org.transcom.exceptions.RoleNotFoundException;
import org.transcom.exceptions.TruckNotFoundException;
import org.transcom.repositories.*;

import java.util.*;

@Mapper(componentModel = "spring",
        imports = {UUID.class,
                CompanyRepository.class,
                RoleRepository.class,
                PhoneRepository.class,
                TruckRepository.class,
                OrderRepository.class,
                FavoriteRepository.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userStatus", constant = "BLOCKED")
    User toUser(UserDtoRequest userDtoRequest);

    default User toUser(UserDtoRequest userDtoRequest,
                        CompanyRepository companyRepository,
                        RoleRepository roleRepository,
                        TruckRepository truckRepository,
                        OrderRepository orderRepository) {
        User user = toUser(userDtoRequest);
        user.setPhones(mapPhoneNumbersToPhones(userDtoRequest.getPhoneNumbers(), user));
        user.setCompany(mapCompanyIdToCompany(companyRepository, userDtoRequest.getCompanyId()));
        user.setUserRoles(mapRoleNamesToRoles(userDtoRequest.getRoleNames(), roleRepository));
        user.setTrucks(mapTruckIdsToTrucks(userDtoRequest.getTruckIds(), truckRepository));
        user.setOrders(mapOrderIdsToOrders(userDtoRequest.getOrdersIds(), orderRepository));
        user.setFavorites(mergeAllFavoritesToFavorites(user,
                userDtoRequest.getFavoriteTrucks(),
                userDtoRequest.getFavoriteOrders(),
                truckRepository,
                orderRepository));
        return user;
    }

    default List<Phone> mapPhoneNumbersToPhones(List<String> phoneNumbers, User user) {
        return phoneNumbers.stream()
                .map(phoneNumber -> Phone.builder()
                        .phoneNumber(phoneNumber)
                        .user(user)
                        .build())
                .toList();
    }

    default Company mapCompanyIdToCompany(CompanyRepository companyRepository, Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("{error.company_not_found}"));
    }

    default Set<Role> mapRoleNamesToRoles(List<String> roleNames, RoleRepository roleRepository) {
        Set<Role> roles = new HashSet<>();
        roleNames.forEach(roleName -> {
            Role role = roleRepository.findByRoleName(roleName);
            if (role == null) {
                throw new RoleNotFoundException("{error.role_not_found}");
            }
            roles.add(role);
        });
        return roles;
    }

    default List<Truck> mapTruckIdsToTrucks(List<UUID> truckIds, TruckRepository truckRepository) {
        if (truckIds == null) {
            return new ArrayList<>();
        }

        List<Truck> trucks = truckRepository.findAllById(truckIds);

        if (trucks.size() != truckIds.size()) {
            throw new TruckNotFoundException("{error.truck_not_found}");
        }

        return trucks;
    }

    default List<Order> mapOrderIdsToOrders(List<UUID> orderIds, OrderRepository orderRepository) {
        if (orderIds == null) {
            return new ArrayList<>();
        }

        List<Order> orders = orderRepository.findAllById(orderIds);

        if (orders.size() != orderIds.size()) {
            throw new OrderNotFoundException("{error.order_not_found}");
        }

        return orders;
    }

    default List<Favorite> mergeAllFavoritesToFavorites(User user,
                                                        List<UUID> favoriteTrucks,
                                                        List<UUID> favoriteOrders,
                                                        TruckRepository truckRepository,
                                                        OrderRepository orderRepository) {
        List<Favorite> favorites = new ArrayList<>();
        if (!(favoriteOrders == null)) {
            favoriteOrders.forEach(orderId -> {
                Order orderById = orderRepository.findById(orderId)
                        .orElseThrow(() -> new OrderNotFoundException("{error.order_not_found}"));
                favorites.add(Favorite.builder()
                        .user(user)
                        .order(orderById)
                        .build());
            });
        }
        if (!(favoriteTrucks == null)) {
            favoriteTrucks.forEach(truckId -> {
                Truck truckById = truckRepository.findById(truckId)
                        .orElseThrow(() -> new TruckNotFoundException("{error.truck_not_found}"));
                favorites.add(Favorite.builder()
                        .user(user)
                        .truck(truckById)
                        .build());
            });
        }
        return favorites;
    }


    @Mapping(target = "phoneNumbers", source = "phones")
    UserDtoResponse toUserDtoResponse(User user);

    default UserDtoResponse toUserDtoResponse(User user, FavoriteRepository favoriteRepository) {
        UserDtoResponse userDtoResponse = toUserDtoResponse(user);

        userDtoResponse.setFavoriteOrders(separateFavoriteOrdersFromFavorites(user.getId(), user.getFavorites(), favoriteRepository));
        userDtoResponse.setFavoriteTrucks(separateFavoriteTrucksFromFavorites(user.getId(), user.getFavorites(), favoriteRepository));

        return userDtoResponse;
    }

    default List<String> mapPhonesToPhoneNumbers(List<Phone> phones) {
        return phones.stream()
                .map(Phone::getPhoneNumber)
                .toList();
    }

    default List<UUID> separateFavoriteOrdersFromFavorites(UUID userId, List<Favorite> favorites, FavoriteRepository favoriteRepository) {

        if (favorites != null && !favorites.isEmpty()) {
            List<Order> favoriteOrderIdsByUserId = favoriteRepository.findFavoriteOrdersByUserId(userId);
            return favoriteOrderIdsByUserId.stream()
                    .map(Order::getId)
                    .toList();
        }

        return new ArrayList<>();
    }

    default List<UUID> separateFavoriteTrucksFromFavorites(UUID userId, List<Favorite> favorites, FavoriteRepository favoriteRepository) {

        if (favorites != null && !favorites.isEmpty()) {
            List<Truck> favoriteTruckIdsByUserId = favoriteRepository.findFavoriteTrucksByUserId(userId);
            return favoriteTruckIdsByUserId.stream()
                    .map(Truck::getId)
                    .toList();
        }

        return new ArrayList<>();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "phones", expression = "java(mapPhoneNumbersToPhones(userDtoRequest.getPhoneNumbers(), user))")
    default void updateUserFromDto(UserDtoRequest userDtoRequest,
                                   @MappingTarget User user,
                                   CompanyRepository companyRepository,
                                   RoleRepository roleRepository,
                                   TruckRepository truckRepository,
                                   OrderRepository orderRepository) {
        if (userDtoRequest.getPassword() != null && !userDtoRequest.getPassword().isEmpty()) {
            user.setPassword(userDtoRequest.getPassword());
        }
        if (userDtoRequest.getFirstName() != null && !userDtoRequest.getFirstName().isEmpty()) {
            user.setFirstName(userDtoRequest.getFirstName());
        }
        if (userDtoRequest.getLastName() != null && !userDtoRequest.getLastName().isEmpty()) {
            user.setLastName(userDtoRequest.getLastName());
        }
        if (userDtoRequest.getEmail() != null && !userDtoRequest.getEmail().isEmpty()) {
            user.setEmail(userDtoRequest.getEmail());
        }

        user.setPhones(mapPhoneNumbersToPhones(userDtoRequest.getPhoneNumbers(), user));

        if (userDtoRequest.getCompanyId() != null) {
            user.setCompany(companyRepository.findById(userDtoRequest.getCompanyId()).orElseThrow(() ->
                    new CompanyNotFoundException("{error.company_not_found}")));
        }
        if (userDtoRequest.getRoleNames() != null && !userDtoRequest.getRoleNames().isEmpty()) {
            user.setUserRoles(mapRoleNamesToRoles(userDtoRequest.getRoleNames(), roleRepository));
        }

        user.setUserStatus(userDtoRequest.getUserStatus());

        if (userDtoRequest.getTruckIds() != null) {
            user.setTrucks(mapTruckIdsToTrucks(userDtoRequest.getTruckIds(), truckRepository));
        }
        if (userDtoRequest.getOrdersIds() != null) {
            user.setOrders(mapOrderIdsToOrders(userDtoRequest.getOrdersIds(), orderRepository));
        }
        if (userDtoRequest.getFavoriteTrucks() != null || userDtoRequest.getFavoriteOrders() != null) {
            user.setFavorites(mergeAllFavoritesToFavorites(user,
                    userDtoRequest.getFavoriteTrucks(),
                    userDtoRequest.getFavoriteOrders(),
                    truckRepository,
                    orderRepository));
        }
    }

}
