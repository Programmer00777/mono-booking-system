package com.mono.bookingsystem.paymentsystem.dto;

import com.mono.bookingsystem.paymentsystem.entity.Status;

import java.util.UUID;

public record PaymentStatusDto(UUID paymentId, Status status) {

}
