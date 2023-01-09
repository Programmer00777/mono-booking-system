package com.mono.bookingsystem.paymentsystem.exception;

public class InvalidPaymentException extends IllegalArgumentException {
    public InvalidPaymentException() {
    }

    public InvalidPaymentException(String s) {
        super(s);
    }

    public InvalidPaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
