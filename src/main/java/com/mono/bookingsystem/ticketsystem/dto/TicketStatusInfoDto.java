package com.mono.bookingsystem.ticketsystem.dto;

import com.mono.bookingsystem.ticketsystem.entity.Trip;

public class TicketStatusInfoDto {
    private Trip trip;
    private String status;

    public TicketStatusInfoDto(Trip trip, String status) {
        this.trip = trip;
        this.status = status;
    }

    public Trip getTripInfo() {
        return this.trip;
    }

    public String getStatus() {
        return this.status;
    }
}
