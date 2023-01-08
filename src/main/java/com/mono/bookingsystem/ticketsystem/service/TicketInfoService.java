package com.mono.bookingsystem.ticketsystem.service;

import com.mono.bookingsystem.ticketsystem.entity.Ticket;
import com.mono.bookingsystem.ticketsystem.exception.TicketNotFoundException;
import com.mono.bookingsystem.ticketsystem.repository.TicketRepository;
import com.mono.bookingsystem.ticketsystem.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Accepts: Ticket ID
 * Returns: Trip info, payment status
 *
 * Communicates with: {@link com.mono.bookingsystem.paymentsystem.service.FetchPaymentStatusService}
 */
@Service
public class TicketInfoService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;

    @Autowired
    public TicketInfoService(TicketRepository ticketRepository, TripRepository tripRepository) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
    }

    public String getTicketInfo(UUID ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID must be provided");
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            throw new TicketNotFoundException("Ticket with ID " + ticketId + " not found");
        } else {
            String info = "Trip info:\n"
                          + tripRepository.findById(ticket.getTripId())
                          + "Payment status: ";

            // FIXME: how can I fetch the payment status if I want ticket system and payment system
            // FIXME: to communicate through HTTP
            // Maybe I can leave the contents of the info as it is and concatenate the status itself
            // in the controller (which isn't a good decision I think)

            return info;
        }
    }

}
