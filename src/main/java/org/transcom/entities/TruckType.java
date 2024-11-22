package org.transcom.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "truck_types")
@AllArgsConstructor
public class TruckType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_name", nullable = false, unique = true)
    private String shortName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "length_meters", nullable = false)
    private double lengthMeters;

    @Column(name = "capacity_cubic_units", nullable = false)
    private double capacityCubicUnits;

    @Column(name = "image_truck_type_name")
    private String imageUrl;

    @OneToMany(mappedBy = "truckType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Truck> trucks;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TruckType truckType = (TruckType) o;
        return Double.compare(lengthMeters, truckType.lengthMeters) == 0 &&
                Double.compare(capacityCubicUnits, truckType.capacityCubicUnits) == 0 &&
                Objects.equals(id, truckType.id) &&
                Objects.equals(shortName, truckType.shortName) &&
                Objects.equals(fullName, truckType.fullName) &&
                Objects.equals(imageUrl, truckType.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, fullName, lengthMeters, capacityCubicUnits, imageUrl, trucks);
    }
}
