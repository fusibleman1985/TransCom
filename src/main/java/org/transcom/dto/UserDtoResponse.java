package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.transcom.entities.Order;
import org.transcom.entities.User;
import org.transcom.enums.UserStatus;

import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponse {
    private UUID id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private String createdAt;
    private String updatedAt;
    private List<String> phones;
    private List<Order> orders;
}