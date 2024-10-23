package org.transcom.model;

import lombok.Getter;
import lombok.Setter;
import org.transcom.enums.UserRole;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Set<UserRole> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private boolean active;
    private boolean blocked;
}
