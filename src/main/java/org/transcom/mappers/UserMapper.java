package org.transcom.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.Phone;
import org.transcom.entities.User;
import org.transcom.repositories.UserRepository;
import org.transcom.utils.UtilsUser;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UtilsUser utilsUser;
    private final UserRepository userRepository;

    // UserDtoRequest -> User
    public User toUser(UserDtoRequest userDtoRequest) {
        User user = new User();

        List<Phone> phones = userDtoRequest.getPhoneNumbers().stream()
                .map(phoneNumber -> Phone.builder()
                        .phoneNumber(phoneNumber)
                        .user(user)
                        .build())
                .toList();

        user.setLogin(userDtoRequest.getLogin());
        user.setPassword(utilsUser.hashPassword(userDtoRequest.getPassword()));
        user.setFirstName(userDtoRequest.getFirstName());
        user.setLastName(userDtoRequest.getLastName());
        user.setEmail(userDtoRequest.getEmail());
        user.setUserStatus(userDtoRequest.getUserStatus());
        user.setPhones(phones);
        user.setOrders(userDtoRequest.getOrders());
        return user;
    }

    // User -> UserDtoResponse
    public UserDtoResponse toUserDtoResponse(User user) {
        List<String> phoneNumbers = user.getPhones().stream()
                .map(Phone::getPhoneNumber)
                .collect(Collectors.toList());

        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setUuid(user.getId());
        userDtoResponse.setLogin(user.getLogin());
        userDtoResponse.setFirstName(user.getFirstName());
        userDtoResponse.setLastName(user.getLastName());
        userDtoResponse.setEmail(user.getEmail());
        userDtoResponse.setUserStatus(user.getUserStatus());
        userDtoResponse.setPhoneNumbers(phoneNumbers);
        userDtoResponse.setOrders(user.getOrders());

        return userDtoResponse;
    }

    // update User with UserDtoRequest
    public void updateUserFromDto(UserDtoRequest userDtoRequest, User user) {
        if (userDtoRequest.getLogin() != null && !userDtoRequest.getLogin().isEmpty()) {
            user.setLogin(userDtoRequest.getLogin());
        }

        if (userDtoRequest.getPassword() != null && !userDtoRequest.getPassword().isEmpty()) {
            user.setPassword(utilsUser.hashPassword(userDtoRequest.getPassword()));
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

        if (userDtoRequest.getUserStatus() != null) {
            user.setUserStatus(userDtoRequest.getUserStatus());
        }

        if (userDtoRequest.getPhoneNumbers() != null && !userDtoRequest.getPhoneNumbers().isEmpty()) {
            List<Phone> updatedPhones = userDtoRequest.getPhoneNumbers().stream()
                    .map(phoneNumber -> new Phone(null, phoneNumber, user)) // создаем Phone с текущим user как владельцем
                    .toList();
            user.getPhones().clear();
            userRepository.save(user);
            user.getPhones().addAll(updatedPhones);
        }

        if (userDtoRequest.getOrders() != null && !userDtoRequest.getOrders().isEmpty()) {
            user.setOrders(userDtoRequest.getOrders());
        }
    }
}
