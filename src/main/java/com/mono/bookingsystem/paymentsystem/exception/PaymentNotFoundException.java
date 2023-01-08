package com.mono.bookingsystem.paymentsystem.exception;

public class PaymentNotFoundException extends NullPointerException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
