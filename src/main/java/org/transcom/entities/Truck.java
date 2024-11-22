package org.transcom.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.transcom.entities.enums.TruckStatus;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "length")
    private int length;

    @Column(name = "weight")
    private int weight;

    @Column(name = "capacity")
    private double capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "truck_status")
    private TruckStatus truckStatus;

    @Column(name = "location")
    private String location;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_trucks",
            joinColumns = {@JoinColumn(name = "truck_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private TruckType truckType;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return length == truck.length
                && weight == truck.weight
                && Objects.equals(id, truck.id)
                && Objects.equals(capacity, truck.capacity)
                && truckStatus == truck.truckStatus
                && Objects.equals(location, truck.location)
                && truckType.getShortName().equals(truck.truckType.getShortName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, length, weight, capacity, truckStatus, location, truckType);
    }
}
