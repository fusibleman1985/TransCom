package org.transcom.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Company {
    private Long id;
    String companyName;
    String licenseId;
    double score;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
    boolean active;
    boolean blocked;
    boolean deleted;
}