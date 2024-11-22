package org.transcom.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.Phone;
import org.transcom.entities.User;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userStatus", constant = "BLOCKED")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "phones", expression = "java(mapPhoneNumbersToPhones(userDtoRequest.getPhoneNumbers(), user))")
    User toUser(UserDtoRequest userDtoRequest);

    @Mapping(target = "phoneNumbers", ignore = true)
    UserDtoResponse toUserDtoResponse(User user);

    default UserDtoResponse toUserDtoResponseWithPhones(User user) {
        UserDtoResponse userDtoResponse = toUserDtoResponse(user);
        userDtoResponse.setPhoneNumbers(mapPhonesToPhoneNumbers(user.getPhones()));
        return userDtoResponse;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "phones", expression = "java(mapPhoneNumbersToPhones(userDtoRequest.getPhoneNumbers(), user))")
    default void updateUserFromDto(UserDtoRequest userDtoRequest, @MappingTarget User user) {
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
        user.setUserStatus(userDtoRequest.getUserStatus());

        user.setPhones(mapPhoneNumbersToPhones(userDtoRequest.getPhoneNumbers(), user));
    }

    @Named("mapPhonesToPhoneNumbers")
    default List<String> mapPhonesToPhoneNumbers(List<Phone> phones) {
        return phones.stream()
                .map(Phone::getPhoneNumber)
                .toList();
    }

    default List<String> map(List<Phone> phones) {
        return phones.stream()
                .map(Phone::getPhoneNumber)
                .toList();
    }


    default List<Phone> mapPhoneNumbersToPhones(List<String> phoneNumbers, User user) {
        return phoneNumbers.stream()
                .map(phoneNumber -> Phone.builder()
                        .phoneNumber(phoneNumber)
                        .user(user)
                        .build())
                .toList();
    }

}
