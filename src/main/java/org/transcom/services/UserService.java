package org.transcom.services;

import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDtoResponse createUser(UserDtoRequest userDto);

    List<UserDtoResponse> findAllUsers();

    UserDtoResponse findUserById(UUID id);

    UserDtoResponse updateUser(UUID id, UserDtoRequest userDtoRequest);

    void deleteUser(UUID id);
}
