package com.mono.bookingsystem.ticketsystem.service;

import com.mono.bookingsystem.paymentsystem.dto.PaymentStatusDto;
import com.mono.bookingsystem.ticketsystem.dto.TicketInfoDto;
import com.mono.bookingsystem.ticketsystem.entity.Ticket;
import com.mono.bookingsystem.ticketsystem.entity.Trip;
import com.mono.bookingsystem.ticketsystem.exception.TicketNotFoundException;
import com.mono.bookingsystem.ticketsystem.exception.TripNotFoundException;
import com.mono.bookingsystem.ticketsystem.repository.TicketRepository;
import com.mono.bookingsystem.ticketsystem.repository.TripRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Accepts: Ticket ID
 * Returns: Trip info, payment status
 * <p>
 * Communicates with: {@link com.mono.bookingsystem.paymentsystem.service.FetchPaymentStatusService}
 */
@Service
public class TicketInfoService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    @Value("${mono.paymentsystem.status.uri}")
    private String generalPaymentStatusUri;
    private final RestTemplate template = new RestTemplate();

    @Autowired
    public TicketInfoService(TicketRepository ticketRepository, TripRepository tripRepository) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
    }

    public TicketInfoDto getTicketInfoById(UUID ticketId, HttpServletRequest request) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID must be provided");
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            throw new TicketNotFoundException("Ticket with ID " + ticketId + " not found");
        } else {
            String fetchedStatus = fetchPaymentStatus(ticket, request);
            Trip trip = fetchTripInfo(ticket.getTripId().toString());
            return new TicketInfoDto(ticket.getId(), trip, fetchedStatus);
        }
    }

    public List<TicketInfoDto> getTicketInfoList(HttpServletRequest request) {
        List<TicketInfoDto> ticketInfoDtos = ticketRepository.findAll().stream()
                .map(ticket -> new TicketInfoDto(ticket.getId(), fetchTripInfo(ticket.getTripId().toString()),
                                                 fetchPaymentStatus(ticket, request))).toList();

        if (ticketInfoDtos.size() == 0) {
            throw new TripNotFoundException("No tickets and/or trips found");
        } else {
            return ticketInfoDtos;
        }
    }

    public void updateTripAvailable(UUID paymentId) {
        Ticket ticket = ticketRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new TicketNotFoundException("No ticket with payment ID " + paymentId + " found"));
        Trip trip = tripRepository.findById(ticket.getTripId())
                .orElseThrow(() -> new TripNotFoundException("No trip with ID " + ticket.getTripId() + " found"));
        trip.setAvailable(trip.getAvailable() + 1);
        tripRepository.save(trip);
    }

    private String fetchPaymentStatus(Ticket ticket, HttpServletRequest request) {
        String uri = request.getRequestURL()
                .substring(0, request.getRequestURL().indexOf(request.getRequestURI()))
                + generalPaymentStatusUri + ticket.getPaymentId();
        return template.getForObject(uri, PaymentStatusDto.class).status().toString();
    }

    private Trip fetchTripInfo(String tripId) {
        Optional<Trip> trip = tripRepository.findById(UUID.fromString(tripId));
        if (trip.isPresent()) return trip.get();
        else throw new TripNotFoundException("Trip with ID " + tripId + " not found");
    }
}
