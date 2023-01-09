package com.mono.bookingsystem.ticketsystem.exception;

public class NoTicketsAvailableException extends RuntimeException {
    public NoTicketsAvailableException() {
    }

    public NoTicketsAvailableException(String message) {
        super(message);
    }
}
