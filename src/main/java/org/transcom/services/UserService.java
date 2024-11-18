package org.transcom.services;

import jakarta.validation.Valid;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDtoResponse createUser(@Valid UserDtoRequest userDto);

    List<UserDtoResponse> findAllUsers();

    UserDtoResponse findUserById(UUID id);

    UserDtoResponse updateUser(UUID id, UserDtoRequest userDtoRequest);

    boolean deleteUser(UUID id);
}
