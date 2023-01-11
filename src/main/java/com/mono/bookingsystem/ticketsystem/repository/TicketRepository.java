package com.mono.bookingsystem.ticketsystem.repository;

import com.mono.bookingsystem.ticketsystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
//    @Query("select t from Ticket t where t.paymentId = ?")
    public Optional<Ticket> findByPaymentId(UUID paymentId);
}
