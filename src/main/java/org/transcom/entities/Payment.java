package org.transcom.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private UUID id;

//    @ManyToOne
    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "tariff_plan_id", nullable = false)
    private TariffPlan tariffPlan;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_date")
    private Timestamp paymentDate;
}