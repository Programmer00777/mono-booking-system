package com.mono.bookingsystem.ticketsystem.dto;

import com.mono.bookingsystem.ticketsystem.entity.Trip;

import java.util.UUID;

public class TicketInfoDto {
    private UUID ticketId;
    private Trip trip;
    private String status;

    public TicketInfoDto(Trip trip, String status) {
        this.trip = trip;
        this.status = status;
    }

    public TicketInfoDto(UUID ticketId, Trip trip, String status) {
        this.ticketId = ticketId;
        this.trip = trip;
        this.status = status;
    }

    public Trip getTripInfo() {
        return this.trip;
    }

    public String getStatus() {
        return this.status;
    }

    public UUID getTicketId() {
        return ticketId;
    }
}
