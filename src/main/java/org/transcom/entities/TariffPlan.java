package org.transcom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tariff_plans")
public class TariffPlan {

    @Id
    @GeneratedValue()
    private int id;

    @Column(name="tariff_name")
    private String name;

    @Column(name="price")
    private Double price;

    @Column(name="features")
    private String features;

    @Column(name="validity_period")
    private Integer validityPeriod;

    @Column(name="discount")
    private Double discount;
}
