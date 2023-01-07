package com.mono.bookingsystem.paymentsystem.repository;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
