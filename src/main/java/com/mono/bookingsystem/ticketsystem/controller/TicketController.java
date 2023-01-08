package com.mono.bookingsystem.ticketsystem.controller;

import com.mono.bookingsystem.ticketsystem.service.TicketInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/ticket")
public class TicketController {

    private final TicketInfoService ticketInfoService;

    @Autowired
    public TicketController(TicketInfoService ticketInfoService) {
        this.ticketInfoService = ticketInfoService;
    }

    @GetMapping(path = "/info/{ticketId}")
    public String getTicketInfo(@PathVariable("ticketId") String id) {
        return ticketInfoService.getTicketInfo(UUID.fromString(id));
    }
}
