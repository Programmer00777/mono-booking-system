package com.mono.bookingsystem.paymentsystem.service;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.entity.Status;
import com.mono.bookingsystem.paymentsystem.exception.PaymentNotFoundException;
import com.mono.bookingsystem.paymentsystem.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Accepts: unique payment ID
 * Returns: NEW/FAILED/DONE status (random)
 *
 * Communicates with: {@link com.mono.bookingsystem.ticketsystem.service.TicketInfoService}
 */
@Service
public class FetchPaymentStatusService {

    private final PaymentRepository paymentRepository;

    public FetchPaymentStatusService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Status fetchStatus(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) throw new PaymentNotFoundException("Payment with ID " + paymentId + " not found");
        else {
            return payment.getStatus();
        }
    }
}
