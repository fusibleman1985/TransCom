package org.transcom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.transcom.dto.UserDto;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.Phone;
import org.transcom.entities.User;
import org.transcom.entities.enums.UserStatus;
import org.transcom.factories.UserFactory;
import org.transcom.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserFactory userFactory;

    @Override
    public UserDtoResponse createUser(UserDto userDto) {
        User newUser = userFactory.createUser(userDto);
        User savedUser = userRepository.save(newUser);
        return convertUserToUserDtoResponse(savedUser);
    }

    @Override
    public List<UserDtoResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertUserToUserDtoResponse)
                .toList();
    }

    @Override
    public UserDtoResponse findUserById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return convertUserToUserDtoResponse(user);
        }
        return null;
    }

    @Override
    public UserDtoResponse updateUser(UserDto userDto) {
        User userByLogin = userRepository.findByLogin(userDto.getLogin()).orElse(null);

        if (userByLogin != null) {
            if (userDto.getPassword() != null) {
                userByLogin.setPassword(userDto.getPassword());
            }
            if (userDto.getFirstName() != null) {
                userByLogin.setFirstName(userDto.getFirstName());
            }
            if (userDto.getLastName() != null) {
                userByLogin.setLastName(userDto.getLastName());
            }
            if (userDto.getEmail() != null) {
                userByLogin.setEmail(userDto.getEmail());
            }
            userByLogin.setUserStatus(userDto.getUserStatus());
            if (userDto.getPhones() != null) {
                List<Phone> updatedPhones = userByLogin.getPhones().stream()
                        .map(phone -> Phone.builder()
                                .phoneNumber(phone.getPhoneNumber())
                                .build())
                        .toList();
                userByLogin.getPhones().clear();
                userByLogin.getPhones().addAll(updatedPhones);
            }
            User updatedUser = userRepository.save(userByLogin);
            return convertUserToUserDtoResponse(updatedUser);
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

    private UserDtoResponse convertUserToUserDtoResponse(User user) {
        List<String> phones = user.getPhones().stream()
                .map(Phone::getPhoneNumber)
                .toList();

        return UserDtoResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userStatus(user.getUserStatus())
                .createdAt(user.getCreatedAt().toString())
                .updatedAt(user.getUpdatedAt().toString())
                .phones(phones)
                .orders(user.getOrders())
                .build();
    }
}
