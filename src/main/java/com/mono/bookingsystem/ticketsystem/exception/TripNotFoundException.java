package com.mono.bookingsystem.ticketsystem.exception;

public class TripNotFoundException extends NullPointerException {
    public TripNotFoundException() {
    }

    public TripNotFoundException(String s) {
        super(s);
    }
}
