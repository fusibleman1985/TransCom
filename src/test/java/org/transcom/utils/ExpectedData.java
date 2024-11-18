package org.transcom.utils;

import org.transcom.dto.UserDtoRequest;
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

    public static final UUID USER_ID = UUID.fromString("111e4567-e89b-12d3-a456-426614174000");
    public static final String USER_LOGIN = "user1";
    public static final String USER_PASS = "Password123!";
    public static final String USER_FIRST_NAME = "John";
    public static final String USER_LAST_NAME = "Doe";
    public static final String USER_EMAIL = "john.doe@example.com";
    public static final String USER_PHONE = "+1234567890";
    public static final UUID ORDER_ID = UUID.fromString("333e4567-e89b-12d3-a456-426614174000");
    public static final String ORDER_DESCRIPTION = "Order for electronics";

    public static User returnUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASS);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setEmail(USER_EMAIL);

        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone(1L, USER_PHONE, user));
        user.setPhones(phones);

        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setWeight(5);
        order.setPrice(BigDecimal.valueOf(49.99));
        order.setDescription(ORDER_DESCRIPTION);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setUser(user);
        orders.add(order);

        user.setOrders(orders);
        return user;
    }

    public static UserDtoRequest returnUserDtoRequest() {
        UserDtoRequest userDtoRequest = new UserDtoRequest();
        userDtoRequest.setLogin(USER_LOGIN);
        userDtoRequest.setPassword(USER_PASS);
        userDtoRequest.setFirstName(USER_FIRST_NAME);
        userDtoRequest.setLastName(USER_LAST_NAME);
        userDtoRequest.setEmail(USER_EMAIL);
        userDtoRequest.setUserStatus(UserStatus.ACTIVE);

        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(USER_PHONE);

        userDtoRequest.setPhoneNumbers(phoneNumbers);
        userDtoRequest.setOrders(new ArrayList<>());
        return userDtoRequest;
    }

}
