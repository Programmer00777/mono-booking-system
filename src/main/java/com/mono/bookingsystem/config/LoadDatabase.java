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

@Configuration
public class LoadDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PaymentRepository paymentRepository,
                                   TicketRepository ticketRepository,
                                   TripRepository tripRepository) {
        return args -> {
            paymentRepository.save(new Payment("Sergey Chernikov", 600.45, Status.DONE));
            paymentRepository.save(new Payment("John Doe", 400.00, Status.FAILED));
            paymentRepository.save(new Payment("Alex Brown", 500.00));
            paymentRepository.findAll().forEach(payment -> LOGGER.info("Preloaded " + payment));

            tripRepository.save(new Trip("Dnipro",
                                           "Kharkiv",
                                               LocalDateTime.of(2022, 1, 14, 11, 0, 0),
                                         600.45,
                                      40));
            tripRepository.save(new Trip("Kiyv",
                                          "Zhytomyr",
                                              LocalDateTime.of(2022, 1, 15, 9, 30, 0),
                                        400.00,
                                     31));
            tripRepository.save(new Trip("Odesa",
                                          "Mykolaiv",
                                              LocalDateTime.of(2022, 2, 4, 15, 45, 0),
                                        500.00,
                                     37));
            tripRepository.findAll().forEach(trip -> LOGGER.info("Preloaded " + trip));

            ticketRepository.save(new Ticket("Sergey Chernikov", 1L, 1L));
            ticketRepository.save(new Ticket("John Doe", 2L, 2L));
            ticketRepository.save(new Ticket("Alex Brown", 3L, 3L));
            ticketRepository.findAll().forEach(ticket -> LOGGER.info("Preloaded: " + ticket));
        };
    }
}