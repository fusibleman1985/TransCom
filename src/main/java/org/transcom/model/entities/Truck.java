package org.transcom.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.transcom.model.enams.TruckType;
import org.transcom.model.enams.TruckStatus;

import java.util.UUID;

@Getter
@Setter
public class Truck {
    private UUID id;
    private TruckType type;
    private int feet;
    private int weight;
    private Double capacity;
    private TruckStatus status;
    private String location;
    private User driverId;
}
