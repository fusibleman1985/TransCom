package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transcom.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}