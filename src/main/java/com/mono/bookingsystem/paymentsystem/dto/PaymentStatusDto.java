package com.mono.bookingsystem.paymentsystem.dto;

import com.mono.bookingsystem.paymentsystem.entity.Status;

import java.util.UUID;

public class PaymentStatusDto {

    private UUID paymentId;
    private Status status;

    public PaymentStatusDto(UUID paymentId, Status status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public Status getStatus() {
        return status;
    }
}
