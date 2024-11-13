package org.transcom.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.transcom.entities.enums.TruckStatus;

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
    private Double capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "truck_status")
    private TruckStatus truckStatus;

    @Column(name = "location")
    private String location;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @Column(name = "user_id")
//    private List<User> user;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonManagedReference
    private TruckType truckType;
}
