package org.transcom.exceptions.enums;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    ORDER_NOT_FOUND("Order not found");

    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
