package com.mono.bookingsystem.ticketsystem.controller;

import com.mono.bookingsystem.ticketsystem.dto.TicketInfoDto;
import com.mono.bookingsystem.ticketsystem.entity.Ticket;
import com.mono.bookingsystem.ticketsystem.service.BuyTicketService;
import com.mono.bookingsystem.ticketsystem.service.TicketInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/ticket")
public class TicketController {

    private final TicketInfoService ticketInfoService;
    private final BuyTicketService buyTicketService;

    @Autowired
    public TicketController(TicketInfoService ticketInfoService, BuyTicketService buyTicketService) {
        this.ticketInfoService = ticketInfoService;
        this.buyTicketService = buyTicketService;
    }

    @GetMapping(path = "/info/{ticketId}")
    public TicketInfoDto getTicketInfoById(@PathVariable("ticketId") String id, HttpServletRequest request) {
        return ticketInfoService.getTicketInfoById(UUID.fromString(id), request);
    }

    @GetMapping(path = "/info/all")
    public List<TicketInfoDto> getTicketInfoList(HttpServletRequest request) {
        return ticketInfoService.getTicketInfoList(request);
    }

    @PostMapping(path = "/buy")
    public UUID buyTicketAndPay(@RequestBody Ticket ticket, HttpServletRequest request) {
        return buyTicketService.buyTicketAndRequestPayment(ticket, request);
    }
}
