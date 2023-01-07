package com.mono.bookingsystem.ticketsystem.repository;

import com.mono.bookingsystem.ticketsystem.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
