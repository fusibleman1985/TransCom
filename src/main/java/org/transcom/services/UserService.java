package org.transcom.services;

import org.transcom.dto.UserDto;
import org.transcom.dto.UserDtoResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDtoResponse createUser(UserDto userDto);

    List<UserDtoResponse> findAllUsers();

    UserDtoResponse findUserById(UUID id);

    UserDtoResponse updateUser(UserDto user);

    void deleteUser(UUID id);
}
