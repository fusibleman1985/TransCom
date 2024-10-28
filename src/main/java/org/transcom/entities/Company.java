package org.transcom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.transcom.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "companies")
public class Company {

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "company")
    private List<User> user;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "license_id", nullable = false)
    private String licenseId;

    @Column(name = "score")
    private double score;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
