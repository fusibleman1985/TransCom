package org.transcom.entity;

import org.transcom.entity.enums.UserRole;
import org.transcom.entity.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class User {
    private UUID id;
    private String nickname;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
//    private String list<Phone>;
    private Set<UserRole> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserStatus userStatus;
}
