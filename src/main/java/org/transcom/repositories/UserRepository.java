package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transcom.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);
}