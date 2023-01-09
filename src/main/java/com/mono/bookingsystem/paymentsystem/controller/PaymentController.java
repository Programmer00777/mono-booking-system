package com.mono.bookingsystem.paymentsystem.controller;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.entity.Status;
import com.mono.bookingsystem.paymentsystem.exception.InvalidPaymentException;
import com.mono.bookingsystem.paymentsystem.service.CreatePaymentService;
import com.mono.bookingsystem.paymentsystem.service.FetchPaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {

    private final FetchPaymentStatusService fetchPaymentStatusService;
    private final CreatePaymentService createPaymentService;

    @Autowired
    public PaymentController(FetchPaymentStatusService fetchPaymentStatusService, CreatePaymentService createPaymentService) {
        this.fetchPaymentStatusService = fetchPaymentStatusService;
        this.createPaymentService = createPaymentService;
    }

    @GetMapping(path = "/status/all")
    public List<Status> getPaymentStatusList() {
        return fetchPaymentStatusService.fetchStatusList();
    }

    @GetMapping(path = "/status/{paymentId}")
    public Status getPaymentStatusById(@PathVariable("paymentId") String id) {
        return fetchPaymentStatusService.fetchStatusWithId(UUID.fromString(id));
    }

    @PostMapping(path = "/create")
    public UUID createPayment(@RequestBody Payment payment) {
        if (payment.getFullName() == null || payment.getAmount() < 0.0) {
            throw new InvalidPaymentException("Invalid full name or amount");
        } else {
            return createPaymentService.createPayment(payment);
        }
    }

    // @PutMapping...
    // @DeleteMapping...
}
