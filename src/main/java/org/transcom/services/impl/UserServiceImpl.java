package org.transcom.services.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.User;
import org.transcom.entities.enums.ClientStatus;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.mappers.UserMapper;
import org.transcom.repositories.*;
import org.transcom.services.UserService;
import org.transcom.utils.Utils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final Utils utilsUser;
    private final UserMapper userMapper;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final TruckRepository truckRepository;
    private final OrderRepository orderRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public UserDtoResponse createUser(@Valid UserDtoRequest userDtoRequest) {
        User newUser = userMapper.toUser(userDtoRequest,
                companyRepository,
                roleRepository,
                truckRepository,
                orderRepository);
        newUser.setPassword(utilsUser.hashPassword(userDtoRequest.getPassword()));
        User savedUser = userRepository.save(newUser);
        return userMapper.toUserDtoResponse(savedUser);
    }

    @Override
    public List<UserDtoResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> userMapper.toUserDtoResponse(user, favoriteRepository))
                .toList();
    }

    @Override
    public UserDtoResponse findUserById(UUID id) {
        User userById = returnUserById(id);
        return userMapper.toUserDtoResponse(userById, favoriteRepository);
    }

    @Override
    public UserDtoResponse updateUser(UUID id, UserDtoRequest userDtoRequest) {
        User userById = returnUserById(id);
        phoneRepository.deleteAll(userById.getPhones());
        // clearLists ???
        userMapper.updateUserFromDto(userDtoRequest, userById,
                companyRepository,
                roleRepository,
                truckRepository,
                orderRepository);


        userById.setPassword(utilsUser.hashPassword(userDtoRequest.getPassword()));
        User updatedUser = userRepository.save(userById);
        return userMapper.toUserDtoResponse(updatedUser);
    }

    @Override
    public boolean deleteUser(UUID id) {
        User userById = returnUserById(id);
        if (userById.getUserStatus() != ClientStatus.DELETED) {
            userById.setUserStatus(ClientStatus.DELETED);
            userRepository.save(userById);
            return true;
        }
        return false;
    }

    private User returnUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("{error.user_not_found}"));
    }
}
