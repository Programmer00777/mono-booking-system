package com.mono.bookingsystem.paymentsystem.service;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.exception.InvalidPaymentException;
import com.mono.bookingsystem.paymentsystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Accepts: Full name, amount
 * Returns: Unique payment ID
 * <p>
 * Communicates with: {@link com.mono.bookingsystem.ticketsystem.service.BuyTicketService}
 */
@Service
public class CreatePaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public CreatePaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public UUID createPayment(Payment payment) {
        areArgumentsValid(payment.getFullName(), payment.getAmount());
        return paymentRepository.save(payment).getId();
    }

    public UUID createPayment(String fullName, Double amount) {
        areArgumentsValid(fullName, amount);
        Payment payment = new Payment(fullName, amount);
        paymentRepository.save(payment);
        return payment.getId();
    }

    private void areArgumentsValid(String fullName, Double amount) {
        if (fullName == null || amount == null) {
            throw new InvalidPaymentException("Both fullName and amount must be provided");
        }
        if (amount < 0.0) {
            throw new InvalidPaymentException("The amount cannot be less than 0");
        }
    }
}
