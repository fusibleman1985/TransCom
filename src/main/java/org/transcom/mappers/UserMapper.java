package org.transcom.mappers;

import org.mapstruct.*;
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

    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "phoneNumbers", ignore = true)
    UserDtoResponse toUserDtoResponse(User user);

    default UserDtoResponse toUserDtoResponseWithPhones(User user) {
        UserDtoResponse userDtoResponse = toUserDtoResponse(user);
        userDtoResponse.setPhoneNumbers(mapPhonesToPhoneNumbers(user.getPhones()));
        return userDtoResponse;
    }

    @InheritConfiguration(name = "toUserDtoResponse")
    default UserDtoResponse toUserDtoResponse(UserDtoRequest userDtoRequest, User user) {
        UserDtoResponse userDtoResponse = toUserDtoResponse(user);
        userDtoResponse.setPhoneNumbers(userDtoRequest.getPhoneNumbers());
        return userDtoResponse;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "phones", expression = "java(mapPhoneNumbersToPhones(userDtoRequest.getPhoneNumbers(), user))")
    void updateUserFromDto(UserDtoRequest userDtoRequest, @MappingTarget User user);

    @Named("mapPhonesToPhoneNumbers")
    default List<String> mapPhonesToPhoneNumbers(List<Phone> phones) {
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
