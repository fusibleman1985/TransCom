//package org.transcom.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "companies")
//public class Company {
//
//    @Id
//    @Column(name = "company_id")
//    private Long id;
//
//    @Column(name = "user_id")
//    private UUID userId;
//
//    @Column(name = "company_name")
//    private String companyName;
//
//    @Column(name = "license_id")
//    private String licenseId;
//
//    @Column(name = "score")
//    private double score;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//
//    @Column(name = "is_active")
//    private boolean active;
//
//    @Column(name = "is_blocked")
//    private boolean blocked;
//
//    @Column(name = "is_deleted")
//    private boolean deleted;
//}
