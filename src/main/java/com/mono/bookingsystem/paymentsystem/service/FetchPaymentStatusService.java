package com.mono.bookingsystem.paymentsystem.service;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.entity.Status;
import com.mono.bookingsystem.paymentsystem.exception.PaymentNotFoundException;
import com.mono.bookingsystem.paymentsystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Accepts: unique payment ID
 * Returns: NEW/FAILED/DONE status (random)
 * <p>
 * Communicates with: {@link com.mono.bookingsystem.ticketsystem.service.TicketInfoService}
 */
@Service
public class FetchPaymentStatusService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public FetchPaymentStatusService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Status fetchStatusWithId(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment == null) throw new PaymentNotFoundException("Payment with ID " + paymentId + " not found");
        else {
            return payment.getStatus();
        }
    }

    public List<Payment> fetchPaymentList() {
        List<Payment> statusList = paymentRepository.findAll();
        if (statusList.size() == 0) {
            throw new PaymentNotFoundException("There are no payments in the system");
        } else {
            return statusList;
        }
    }

    public void updateStatusById(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with ID " + paymentId + " not found"));
        payment.setStatus(Status.randomStatus());
        paymentRepository.save(payment);
    }
}
