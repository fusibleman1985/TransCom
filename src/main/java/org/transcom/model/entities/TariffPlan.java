package org.transcom.model.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TariffPlan {
    private UUID id;
    private String name;
    private Double price;
    private String features;
    private Integer validityPeriod;
    private Double discount;
}
