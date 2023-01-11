package com.mono.bookingsystem.ticketsystem.controller;

import com.mono.bookingsystem.ticketsystem.exception.NoTicketsAvailableException;
import com.mono.bookingsystem.ticketsystem.exception.TicketNotFoundException;
import com.mono.bookingsystem.ticketsystem.exception.TripNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TicketRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TicketNotFoundException.class, TripNotFoundException.class,
    NoTicketsAvailableException.class, IllegalArgumentException.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
