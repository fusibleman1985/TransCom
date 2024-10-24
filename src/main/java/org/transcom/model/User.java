package org.transcom.model;

import lombok.Getter;
import lombok.Setter;
import org.transcom.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;
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
    private List<Phone> phones;
    private Company companyId;
    private Set<UserRole> roles;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<Payment> payments;
    private TariffPlan tariffPlan;
    private boolean deleted;
    private boolean active;
    private boolean blocked;
}
