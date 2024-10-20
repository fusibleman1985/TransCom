package org.transcom.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private int id;
    private int zipCode;
    private String localityName;
    private String stateName;
    private String countryName;
}
