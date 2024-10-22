package org.transcom.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    private UUID id;
    private UUID userId;
    private UUID tariffPlanId;
    private Double amount;
    private Timestamp paymentDate;
}
