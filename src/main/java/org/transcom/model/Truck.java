package org.transcom.model;

import lombok.Getter;
import lombok.Setter;
import org.transcom.enums.TruckType;
import org.transcom.enums.TruckStatus;

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
