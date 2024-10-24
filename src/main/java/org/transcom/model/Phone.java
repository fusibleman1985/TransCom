package org.transcom.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Phone {
    private Long phoneId;
    private UUID userId;
    private String phoneNumber;
}
