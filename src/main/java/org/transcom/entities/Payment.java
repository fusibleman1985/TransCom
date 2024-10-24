//package org.transcom.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//import java.sql.Timestamp;
//import java.util.UUID;
//
//@Entity
//@Data
//@Table(name = "payments")
//public class Payment {
//
//    @Id
//    @Column(name = "payment_id")
//    private UUID id;
//
//    @Column(name = "user_id")
//    private UUID userId;
//
//    @Column(name = "tariff_plan_id")
//    private UUID tariffPlanId;
//
//    @Column(name = "amount")
//    private Double amount;
//
//    @Column(name = "payment_date")
//    private Timestamp paymentDate;
//}