package org.transcom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tariff_plans")
public class TariffPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tariff_name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "features")
    private String features;

    @Column(name = "validity_period")
    private Integer validityPeriod;

    @Column(name = "discount")
    private Double discount;
}
