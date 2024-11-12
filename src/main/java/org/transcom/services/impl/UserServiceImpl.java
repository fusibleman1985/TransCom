package org.transcom.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.User;
import org.transcom.entities.enums.UserStatus;
import org.transcom.mappers.UserMapper;
import org.transcom.mappers.UserMapper2;
import org.transcom.repositories.UserRepository;
import org.transcom.services.UserService;
import org.transcom.utils.UtilsUser;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UtilsUser utilsUser;
    private final UserMapper userMapper;
    private final UserMapper2 userMapper2;

    @Override
    public UserDtoResponse createUser(UserDtoRequest userDtoRequest) {
        User newUser = userMapper.toUser(userDtoRequest);
        newUser.setPassword(utilsUser.hashPassword(userDtoRequest.getPassword()));
        User savedUser = userRepository.save(newUser);
        UserDtoResponse userDtoResponse = userMapper.toUserDtoResponse(savedUser);
        return userDtoResponse;
    }

    @Override
    public List<UserDtoResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserDtoResponse)
                .toList();
    }

    @Override
    public UserDtoResponse findUserById(UUID id) {
        User userById = userRepository.findById(id).orElse(null);
        if (userById != null) {
            return userMapper.toUserDtoResponse(userById);
        }
        return null;
    }

    @Override
    public UserDtoResponse updateUser(UUID id, UserDtoRequest userDtoRequest) {
        User userById = userRepository.findById(id).orElse(null);
//        if (userById != null) {
//            userMapper.updateUserFromDto(userDtoRequest, userById);
//            User updatedUser = userRepository.save(userById);
//            return userMapper.toUserDtoResponse(updatedUser);
//        }
        if (userById != null) {
            userMapper2.updateUserFromDto(userDtoRequest, userById);
            userById.setPassword(utilsUser.hashPassword(userDtoRequest.getPassword()));
            User updatedUser = userRepository.save(userById);
            return userMapper2.toUserDtoResponseWithPhones(updatedUser);
        }
        return null;
    }

    @Override
    public void deleteUser(UUID id) {
        User userById = userRepository.findById(id).orElse(null);
        if (userById != null && userById.getUserStatus() != UserStatus.DELETED) {
            userById.setUserStatus(UserStatus.DELETED);
            userRepository.save(userById);
        }
    }
}
