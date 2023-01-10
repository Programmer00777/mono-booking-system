package com.mono.bookingsystem.ticketsystem.dto;

import com.mono.bookingsystem.ticketsystem.controller.TicketController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TicketDtoModelAssembler {
    public EntityModel<TicketInfoDto> toModel(TicketInfoDto ticketInfoDto, HttpServletRequest request) {
        return EntityModel.of(ticketInfoDto,
                linkTo(methodOn(TicketController.class)
                        .getTicketInfoById(ticketInfoDto.getTicketId().toString(), request)).withSelfRel(),
                linkTo(methodOn(TicketController.class)
                        .getTicketInfoList(request)).withRel("ticketInfoList"));
    }
}
