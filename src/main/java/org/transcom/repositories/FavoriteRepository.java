package org.transcom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.transcom.entities.Favorite;
import org.transcom.entities.Order;
import org.transcom.entities.Truck;

import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("SELECT f.user.id FROM Favorite f WHERE f.truck.id = :truckId ")
    List<UUID> findUsersWhoFavoritedTruck(@Param("truckId") UUID truckId);

    @Query("SELECT f.user.id FROM Favorite f WHERE f.order.id = :orderId ")
    List<UUID> findUsersWhoFavoritedOrders(@Param("orderId") UUID orderId);

    @Query("SELECT f.truck FROM Favorite f WHERE f.user.id = :userId AND f.truck IS NOT NULL")
    List<Truck> findFavoriteTrucksByUserId(@Param("userId") UUID userId);

    @Query("SELECT f.order FROM Favorite f WHERE f.user.id = :userId AND f.order IS NOT NULL")
    List<Order> findFavoriteOrdersByUserId(@Param("userId") UUID userId);

}
