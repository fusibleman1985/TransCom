package org.transcom.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.transcom.entities.enums.AccessRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "Weight must be greater than zero")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_role")
    private AccessRole accessRole;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "userRoles")
    private List<User> users;
}
