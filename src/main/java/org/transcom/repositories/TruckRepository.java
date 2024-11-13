package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.transcom.entities.Truck;

import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, UUID> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE " +
            "ELSE FALSE END FROM Truck t WHERE t.truckType.shortName = :truckTypeShortName")
    boolean existsByTruckTypeShortName(String truckTypeShortName);
}
