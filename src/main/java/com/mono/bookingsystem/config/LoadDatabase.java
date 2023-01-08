package com.mono.bookingsystem.config;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.entity.Status;
import com.mono.bookingsystem.paymentsystem.repository.PaymentRepository;
import com.mono.bookingsystem.ticketsystem.entity.Ticket;
import com.mono.bookingsystem.ticketsystem.entity.Trip;
import com.mono.bookingsystem.ticketsystem.repository.TicketRepository;
import com.mono.bookingsystem.ticketsystem.repository.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

@Configuration
public class LoadDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PaymentRepository paymentRepository,
                                   TicketRepository ticketRepository,
                                   TripRepository tripRepository) {
        Payment payment1 = new Payment("Sergey Chernikov", 600.45, Status.DONE);
        Payment payment2 = new Payment("John Doe", 400.00, Status.FAILED);
        Payment payment3 = new Payment("Alex Brown", 500.00);

        Trip trip1 = new Trip("Dnipro", "Kharkiv",
                LocalDateTime.of(2022, 1, 14, 11, 0, 0),
                600.45, 40);
        Trip trip2 = new Trip("Kiyv", "Zhytomyr",
                LocalDateTime.of(2022, 1, 15, 9, 30, 0),
                400.0, 31);
        Trip trip3 = new Trip("Odesa", "Mykolaiv",
                LocalDateTime.of(2022, 2, 4, 15, 45, 0),
                500.0, 37);

        return args -> {
            paymentRepository.saveAll(Arrays.asList(payment1, payment2, payment3));
            tripRepository.saveAll(Arrays.asList(trip1, trip2, trip3));

            Ticket ticket1 = new Ticket("Sergey Chernikov", trip1.getId(), payment1.getId());
            Ticket ticket2 = new Ticket("John Doe", trip2.getId(), payment2.getId());
            Ticket ticket3 = new Ticket("Alex Brown", trip3.getId(), payment3.getId());

            ticketRepository.saveAll(Arrays.asList(ticket1, ticket2, ticket3));

            Stream.of(paymentRepository, tripRepository, ticketRepository)
                    .forEach(repo -> {
                        repo.findAll().forEach(item -> LOGGER.info("Preloaded: " + item));
                    });
        };
    }
}