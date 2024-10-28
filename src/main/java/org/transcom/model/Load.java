package org.transcom.model;

import lombok.Getter;
import lombok.Setter;
import org.transcom.enums.LoadStatus;

import java.util.UUID;

@Getter
@Setter
public class Load {
    private UUID id;
    private double weight;
    private int meters;
    private String description;
    private Location pickupLocationId;
    private Location deliveryLocationId;
    private int price;
    private boolean priceVisible;
    private LoadStatus status;
    private boolean privateView;
//    private User shipperId;
//    private Truck assignedTruckId;
}
