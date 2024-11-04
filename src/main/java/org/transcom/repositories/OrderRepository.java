package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.transcom.entities.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
