package org.transcom.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

//@Entity
@Getter
@Setter
//@Table(name = "phones")
public class Phone {

//    @Id
//    @Column(name = "phone_id")
    private Long phoneId;

//    @Column(name = "user_id")
    private UUID userId;

//    @Column(name = "phone_number")
    private String phoneNumber;
}
