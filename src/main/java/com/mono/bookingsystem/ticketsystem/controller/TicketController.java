package com.mono.bookingsystem.ticketsystem.controller;

import com.mono.bookingsystem.ticketsystem.dto.TicketDtoModelAssembler;
import com.mono.bookingsystem.ticketsystem.dto.TicketInfoDto;
import com.mono.bookingsystem.ticketsystem.entity.Ticket;
import com.mono.bookingsystem.ticketsystem.service.BuyTicketService;
import com.mono.bookingsystem.ticketsystem.service.TicketInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/ticket")
public class TicketController {

    private final TicketInfoService ticketInfoService;
    private final BuyTicketService buyTicketService;
    private final TicketDtoModelAssembler ticketDtoModelAssembler;

    @Autowired
    public TicketController(TicketInfoService ticketInfoService,
                            BuyTicketService buyTicketService,
                            TicketDtoModelAssembler ticketDtoModelAssembler) {
        this.ticketInfoService = ticketInfoService;
        this.buyTicketService = buyTicketService;
        this.ticketDtoModelAssembler = ticketDtoModelAssembler;
    }

    @GetMapping(path = "/info/{ticketId}")
    public EntityModel<TicketInfoDto> getTicketInfoById(@PathVariable("ticketId") String id, HttpServletRequest request) {
        TicketInfoDto ticketInfoDto = ticketInfoService.getTicketInfoById(UUID.fromString(id), request);
        return ticketDtoModelAssembler.toModel(ticketInfoDto, request);
    }

    @GetMapping(path = "/info/all")
    public CollectionModel<EntityModel<TicketInfoDto>> getTicketInfoList(HttpServletRequest request) {
        List<EntityModel<TicketInfoDto>> ticketInfoList = ticketInfoService.getTicketInfoList(request)
                .stream().map(ticketInfoDto -> ticketDtoModelAssembler.toModel(ticketInfoDto, request)).toList();

        return CollectionModel
                .of(ticketInfoList, linkTo(methodOn(TicketController.class).getTicketInfoList(request)).withSelfRel());
    }

    @PostMapping(path = "/buy")
    public UUID buyTicketAndPay(@RequestBody Ticket ticket, HttpServletRequest request) {
        return buyTicketService.buyTicketAndRequestPayment(ticket, request);
    }

    @PutMapping(path = "/available/update/{tripId}")
    public void updateAvailable(@PathVariable("tripId") UUID paymentId) {
        ticketInfoService.updateTripAvailable(paymentId);
    }
}
