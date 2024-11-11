package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.transcom.entities.Order;
import org.transcom.entities.enums.UserStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDtoRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private List<String> phoneNumbers;
    private List<Order> orders;
}