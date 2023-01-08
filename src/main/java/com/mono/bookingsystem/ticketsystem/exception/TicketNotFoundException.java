package com.mono.bookingsystem.ticketsystem.exception;

public class TicketNotFoundException extends NullPointerException {

    public TicketNotFoundException() {
    }

    public TicketNotFoundException(String s) {
        super(s);
    }
}
