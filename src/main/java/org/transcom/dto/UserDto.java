package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transcom.entities.Order;
import org.transcom.enums.UserStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private List<String> phones;
    private List<Order> orders;
}