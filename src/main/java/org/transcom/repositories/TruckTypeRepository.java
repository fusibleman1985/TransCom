package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transcom.entities.TruckType;

import java.util.Optional;

public interface TruckTypeRepository extends JpaRepository<TruckType, Long> {
    Optional<TruckType> findByShortName(String shortName);
}
