package com.mono.bookingsystem.paymentsystem.entity;

public enum Status {
    NEW("N"),
    FAILED("F"),
    DONE("D");

    private String statusCode;

    private Status(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
