package org.transcom.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Statistics {
    private UUID id;
    private String[] topRoutes;
    private UUID[] topCarriers;
    private UUID[] topBrokers;
    private Map<String, Double> profitData;
}
