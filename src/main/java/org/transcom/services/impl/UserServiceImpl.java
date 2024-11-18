package org.transcom.services.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.User;
import org.transcom.entities.enums.UserStatus;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.mappers.UserMapper;
import org.transcom.repositories.PhoneRepository;
import org.transcom.repositories.UserRepository;
import org.transcom.services.UserService;
import org.transcom.utils.UtilsUser;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final UtilsUser utilsUser;
    private final UserMapper userMapper;

    @Override
    public UserDtoResponse createUser(@Valid UserDtoRequest userDtoRequest) {
        User newUser = userMapper.toUser(userDtoRequest);
        newUser.setPassword(utilsUser.hashPassword(userDtoRequest.getPassword()));
        User savedUser = userRepository.save(newUser);
        return userMapper.toUserDtoResponseWithPhones(savedUser);
    }

    @Override
    public List<UserDtoResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserDtoResponseWithPhones)
                .toList();
    }

    @Override
    public UserDtoResponse findUserById(UUID id) {
        User userById = userRepository.findById(id).orElse(null);
        if (userById != null) {
            return userMapper.toUserDtoResponseWithPhones(userById);
        }
        return null;
    }

    @Override
    public UserDtoResponse updateUser(UUID id, UserDtoRequest userDtoRequest) {
        User userById = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        phoneRepository.deleteAll(userById.getPhones());
        userMapper.updateUserFromDto(userDtoRequest, userById);
        userById.setPassword(utilsUser.hashPassword(userDtoRequest.getPassword()));
        User updatedUser = userRepository.save(userById);
        return userMapper.toUserDtoResponseWithPhones(updatedUser);
    }

    @Override
    public boolean deleteUser(UUID id) {
        User userById = userRepository.findById(id).orElse(null);
        if (userById != null && userById.getUserStatus() != UserStatus.DELETED) {
            userById.setUserStatus(UserStatus.DELETED);
            userRepository.save(userById);
            return true;
        }
        return false;
    }
}
