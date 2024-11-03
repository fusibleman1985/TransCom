package org.transcom.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.transcom.dto.UserDto;
import org.transcom.entities.Phone;
import org.transcom.entities.User;
import org.transcom.enums.UserStatus;
import org.transcom.utils.UtilsUser;

import java.util.List;
import java.util.UUID;

@Component
public class UserFactory {

    @Autowired
    private UtilsUser utilsUser;

    public User createUser(UserDto userDto) {
        User user = new User();

        user.setId(UUID.randomUUID());
        user.setLogin(userDto.getLogin());
        user.setPassword(utilsUser.hashPassword(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());
        user.setUserStatus(UserStatus.BLOCKED);

        List<Phone> phones = userDto.getPhones().stream()
                .map(phone -> Phone.builder()
                        .phoneNumber(phone)
                        .user(user)
                        .build())
                .toList();

        user.setPhones(phones);
        user.setOrders(userDto.getOrders());
        return user;
    }

}
