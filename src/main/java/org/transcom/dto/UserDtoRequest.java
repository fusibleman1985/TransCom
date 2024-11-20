package org.transcom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.transcom.entities.Order;
import org.transcom.entities.enums.UserStatus;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDtoRequest {
    private String login;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "Password must be at least 8 characters long, include one uppercase letter," +
                    " one lowercase letter, one number, and one special character"
    )
    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private UserStatus userStatus;
    private List<String> phoneNumbers;
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDtoRequest that = (UserDtoRequest) o;

        return Objects.equals(login, that.login)
                && Objects.equals(password, that.password)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(email, that.email)
                && userStatus == that.userStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, firstName, lastName, email, userStatus);
    }
}