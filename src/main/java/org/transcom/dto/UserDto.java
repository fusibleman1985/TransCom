package org.transcom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.transcom.entities.enums.ClientStatus;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {

    @NotBlank(message = "Login cannot be blank")
    private String login;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
            message = "Password must be at least 8 characters long, include one uppercase letter," +
                    " one lowercase letter, one number, and one special character"
    )
    private String password;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    private List<String> phoneNumbers;
    private ClientStatus userStatus;
    private List<UUID> favoriteTrucks;
    private List<UUID> favoriteOrders;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return login.equals(userDto.login) &&
                password.equals(userDto.password) &&
                firstName.equals(userDto.firstName) &&
                lastName.equals(userDto.lastName) &&
                email.equals(userDto.email) &&
                userStatus == userDto.userStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, firstName, lastName, email, userStatus);
    }
}