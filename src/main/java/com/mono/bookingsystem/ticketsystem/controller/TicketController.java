package com.mono.bookingsystem.ticketsystem.controller;

import com.mono.bookingsystem.ticketsystem.dto.TicketInfoDto;
import com.mono.bookingsystem.ticketsystem.service.TicketInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public TicketInfoDto getTicketInfoById(@PathVariable("ticketId") String id, HttpServletRequest request) {
        return ticketInfoService.getTicketInfoById(UUID.fromString(id), request);
    }

    @GetMapping(path = "/info/all")
    public List<TicketInfoDto> getTicketInfoList(HttpServletRequest request) {
        return ticketInfoService.getTicketInfoList(request);
    }

}
