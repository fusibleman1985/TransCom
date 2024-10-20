package org.transcom.model.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Favorite {
    private UUID id;
    private UUID userId;
    private UUID loadId;
    private UUID truckId;
}
