package com.mono.bookingsystem.paymentsystem.controller;

import com.mono.bookingsystem.paymentsystem.service.FetchPaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {

    private final FetchPaymentStatusService fetchPaymentStatusService;

    @Autowired
    public PaymentController(FetchPaymentStatusService fetchPaymentStatusService) {
        this.fetchPaymentStatusService = fetchPaymentStatusService;
    }

    @GetMapping(path = "/status/{paymentId}")
    public String getPaymentStatus(@PathVariable("paymentId") String id) {
        return fetchPaymentStatusService.fetchStatus(UUID.fromString(id)).toString();
    }
}
