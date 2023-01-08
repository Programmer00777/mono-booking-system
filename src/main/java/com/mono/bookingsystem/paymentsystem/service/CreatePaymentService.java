package com.mono.bookingsystem.paymentsystem.service;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Accepts: Full name, amount
 * Returns: Unique payment ID
 *
 * Communicates with: {@link com.mono.bookingsystem.ticketsystem.service.BuyTicketService}
 */
@Service
public class CreatePaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public CreatePaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public UUID createPayment(String fullName, Double amount) {
        Payment payment = new Payment(fullName, amount);
        paymentRepository.save(payment);
        return payment.getId();
    }
}
