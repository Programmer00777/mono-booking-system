package com.mono.bookingsystem.ticketsystem.service;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.ticketsystem.entity.Ticket;
import com.mono.bookingsystem.ticketsystem.entity.Trip;
import com.mono.bookingsystem.ticketsystem.exception.NoTicketsAvailableException;
import com.mono.bookingsystem.ticketsystem.exception.TripNotFoundException;
import com.mono.bookingsystem.ticketsystem.repository.TicketRepository;
import com.mono.bookingsystem.ticketsystem.repository.TripRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

/**
 * Accepts: Full name, trip ID
 * Returns: Ticket ID
 * <p>
 * Communicates with: {@link com.mono.bookingsystem.paymentsystem.service.CreatePaymentService}
 */
@Service
public class BuyTicketService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    @Value("${mono.paymentsystem.request.uri}")
    private String generalPaymentRequestUri;
    private final RestTemplate template = new RestTemplate();

    @Autowired
    public BuyTicketService(TicketRepository ticketRepository, TripRepository tripRepository) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
    }

    public UUID buyTicketAndRequestPayment(Ticket ticket, HttpServletRequest request) {
        String fullName = ticket.getFullName();
        UUID tripId = ticket.getTripId();

        String uri = request.getRequestURL()
                .substring(0, request.getRequestURL().indexOf(request.getRequestURI()))
                + generalPaymentRequestUri;
        Trip trip = fetchTripInfo(tripId);

        if (trip.getAvailable() < 1) {
            throw new NoTicketsAvailableException("No tickets available for trip with ID " + trip.getId());
        } else {
            trip.setAvailable(trip.getAvailable() - 1);
            tripRepository.save(trip);
        }

        Payment payment = new Payment(fullName, trip.getPrice());
        UUID paymentId = template.postForObject(uri, payment, UUID.class);
        ticket.setPaymentId(paymentId);
        ticketRepository.save(ticket);
        return ticket.getId();
    }

    private Trip fetchTripInfo(UUID tripId) {
        Optional<Trip> trip = tripRepository.findById(tripId);
        if (trip.isPresent()) {
            return trip.get();
        } else {
            throw new TripNotFoundException("Trip with ID " + tripId + " not found");
        }
    }
}
