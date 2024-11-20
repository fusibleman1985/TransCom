package org.transcom.utils;

import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.Order;
import org.transcom.entities.Phone;
import org.transcom.entities.User;
import org.transcom.entities.enums.OrderStatus;
import org.transcom.entities.enums.UserStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpectedData {

    public static final UtilsUser utilsUser = new UtilsUser();

    public static User returnUser() {
        User user = new User();
        user.setId(UUID.fromString("111e4567-e89b-12d3-a456-426614174000"));
        user.setLogin("user1");
        user.setPassword(utilsUser.hashPassword("Password123!"));
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserStatus(UserStatus.BLOCKED);
        user.setEmail("john.doe@example.com");

        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone(1L, "+1234567890", user));
        user.setPhones(phones);

        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(UUID.fromString("333e4567-e89b-12d3-a456-426614174000"));
        order.setWeight(5);
        order.setPrice(BigDecimal.valueOf(49.99));
        order.setDescription("Order for electronics");
        order.setOrderStatus(OrderStatus.CREATED);
        order.setUser(user);
        orders.add(order);

        user.setOrders(orders);
        return user;
    }

    public static UserDtoResponse returnUserDtoResponse() {
        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(UUID.fromString("111e4567-e89b-12d3-a456-426614174000"));
        userDtoResponse.setLogin("user1");
        userDtoResponse.setPassword(utilsUser.hashPassword("Password123!"));
        userDtoResponse.setFirstName("John");
        userDtoResponse.setLastName("Doe");
        userDtoResponse.setUserStatus(UserStatus.BLOCKED);
        userDtoResponse.setEmail("john.doe@example.com");

        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("+1234567890");

        userDtoResponse.setPhoneNumbers(phoneNumbers);

        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(UUID.fromString("333e4567-e89b-12d3-a456-426614174000"));
        order.setWeight(5);
        order.setPrice(BigDecimal.valueOf(49.99));
        order.setDescription("Order for electronics");
        order.setOrderStatus(OrderStatus.CREATED);
        order.setUser(returnUser());
        orders.add(order);

        userDtoResponse.setOrders(orders);
        return userDtoResponse;
    }

    public static UserDtoRequest returnUserDtoRequest() {
        UserDtoRequest userDtoRequest = new UserDtoRequest();
        userDtoRequest.setLogin("user1");
        userDtoRequest.setPassword("Password123!");
        userDtoRequest.setFirstName("John");
        userDtoRequest.setLastName("Doe");
        userDtoRequest.setEmail("john.doe@example.com");
        userDtoRequest.setUserStatus(UserStatus.ACTIVE);

        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("+1234567890");

        userDtoRequest.setPhoneNumbers(phoneNumbers);
        userDtoRequest.setOrders(new ArrayList<>());
        return userDtoRequest;
    }

}
