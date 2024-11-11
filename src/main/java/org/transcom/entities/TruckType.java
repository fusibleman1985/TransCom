package org.transcom.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "truckType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Truck> trucks;
}
