package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transcom.entities.TruckType;

public interface TruckTypeRepository extends JpaRepository<TruckType, Long> {
}
