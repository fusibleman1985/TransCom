package org.transcom.exceptions.enums;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    ORDER_NOT_FOUND("Order not found"),
    TRUCK_NOT_FOUND("Truck not found"),
    ERROR_DELETING_TRUCK_TYPE("There are Trucks of this Truck Type"),
    USER_NOT_FOUND("User not found"),
    COMPANY_NOT_FOUND("Company not found");

    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
