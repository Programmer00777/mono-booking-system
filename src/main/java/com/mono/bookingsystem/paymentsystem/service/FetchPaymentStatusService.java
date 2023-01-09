package com.mono.bookingsystem.paymentsystem.service;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.entity.Status;
import com.mono.bookingsystem.paymentsystem.exception.PaymentNotFoundException;
import com.mono.bookingsystem.paymentsystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Accepts: unique payment ID
 * Returns: NEW/FAILED/DONE status (random)
 *
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

    public List<Status> fetchStatusList() {
        List<Status> statusList = paymentRepository.findAll().stream().map(Payment::getStatus).toList();
        if (statusList.size() == 0) {
            throw new PaymentNotFoundException("There are no payments in the system");
        } else {
            return statusList;
        }
    }
}
