package com.mono.bookingsystem.ticketsystem.service;

import com.mono.bookingsystem.ticketsystem.entity.Ticket;
import com.mono.bookingsystem.ticketsystem.exception.TicketNotFoundException;
import com.mono.bookingsystem.ticketsystem.repository.TicketRepository;
import com.mono.bookingsystem.ticketsystem.repository.TripRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    @Value("${mono.paymentsystem.status.uri}")
    private String generalPaymentStatusUri;

    @Autowired
    public TicketInfoService(TicketRepository ticketRepository, TripRepository tripRepository) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
    }

    public String getTicketInfo(UUID ticketId, HttpServletRequest request) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID must be provided");
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            throw new TicketNotFoundException("Ticket with ID " + ticketId + " not found");
        } else {
            System.out.println("===== " + generalPaymentStatusUri);
            String uri = request.getRequestURL()
                                .substring(0, request.getRequestURL().indexOf(request.getRequestURI()))
                                + generalPaymentStatusUri + ticket.getPaymentId();
            RestTemplate template = new RestTemplate();
            String fetchedStatus = template.getForObject(uri, String.class);

            String info = "Trip info:\n"
                          + tripRepository.findById(ticket.getTripId())
                          + "Payment status: " + fetchedStatus;

            return info;
        }
    }

}
