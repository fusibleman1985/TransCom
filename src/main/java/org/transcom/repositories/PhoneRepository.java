package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transcom.entities.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
