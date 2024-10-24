//package org.transcom.entities;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.transcom.enums.UserRole;
//import org.transcom.entities.Company;
//import org.transcom.entities.Payment;
//import org.transcom.entities.Phone;
//import org.transcom.model.TariffPlan;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Set;
//import java.util.UUID;
//
//@Entity
//@Data
//@Table(name = "users")
//public class User {
//
//    @Id
//    @Column(name = "user_id")
//    private UUID id;
//
//    @Column(name = "username")
//    private String userName;
//
//    @Column(name = "password")
//    private String password;
//
//    @Column(name = "first_name")
//    private String firstName;
//
//    @Column(name = "last_name")
//    private String lastName;
//
//    @Column(name = "email")
//    private String email;
//
////    @Column(name = "phones")
////    private List<Phone> phones;
//
//    @Column(name = "company_id")
//    private Company companyId;
//
//    @Column(name = "roles")
//    private Set<UserRole> roles;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//
//    @Column(name = "payments")
//    private List<Payment> payments;
//
//    @Column(name = "tariff_plan")
//    private TariffPlan tariffPlan;
//
//    @Column(name = "is_deleted")
//    private boolean deleted;
//
//    @Column(name = "is_active")
//    private boolean active;
//
//    @Column(name = "is_blocked")
//    private boolean blocked;
//}
