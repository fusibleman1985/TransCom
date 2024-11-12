package org.transcom.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.transcom.entities.enums.TruckStatus;

import java.util.List;
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

    @OneToMany(mappedBy = "truck", cascade = CascadeType.ALL)
    @Column(name = "user_id")
    private List<User> user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference
    @JoinColumn(name = "truck_type_id", referencedColumnName = "id")
    private TruckType truckType;
}
