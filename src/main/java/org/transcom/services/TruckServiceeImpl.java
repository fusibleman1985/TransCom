package org.transcom.services;

import org.transcom.enums.TruckStatus;
import org.transcom.models.Truck;

public class TruckServiceeImpl implements TruckServicee {
    @Override
    public Truck createTruck(Truck truck) {

        String description = TruckStatus.AVAILABLE.getDescription();

        return null;
    }
}
