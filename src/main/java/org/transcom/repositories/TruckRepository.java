package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transcom.entities.Truck;

import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, UUID> {
}
