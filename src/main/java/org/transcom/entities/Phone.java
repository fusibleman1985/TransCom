package org.transcom.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "user_phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long phoneId;

    //    @ManyToOne
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "phone_number")
    private String phoneNumber;
}