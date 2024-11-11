package org.transcom.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.transcom.enums.TruckStatus;

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

    @Column(name = "feet")
    private int feet;

    @Column(name = "weight")
    private int weight;

    @Column(name = "capacity")
    private Double capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "truck_status")
    private TruckStatus truckStatus;

    @Column(name = "location")
    private String location;

//    @OneToMany(cascade = CascadeType.ALL)
//    @Column(name = "driver_id")
//    private User driverId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "truck_type_id", referencedColumnName = "id")
    private TruckType truckType;
}
