package org.transcom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.transcom.enums.Status;
import org.transcom.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "username")
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Phone> phones;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<UserRole> role;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tariff_plan_id")
    private TariffPlan tariffPlan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
