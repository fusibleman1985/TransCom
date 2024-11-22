package org.transcom.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.transcom.entities.enums.CompanyRole;
import org.transcom.entities.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    @NotBlank(message = "CompanyName cannot be blank")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_role")
    private CompanyRole companyRole;

    @Column(name = "license_id", nullable = false)
    @NotBlank(message = "LicenseId cannot be blank")
    private String licenseId;

    @Column(name = "rating")
    @Min(value = 0)
    @Max(value = 100)
    private Integer rating;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_status")
    private UserStatus companyStatus; ///////////////    Переименовать     //////////////////

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Double.compare(rating, company.rating) == 0 && Objects.equals(id, company.id) && Objects.equals(companyName, company.companyName) && Objects.equals(companyRole, company.companyRole) && Objects.equals(licenseId, company.licenseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, companyRole, licenseId, rating);
    }
}