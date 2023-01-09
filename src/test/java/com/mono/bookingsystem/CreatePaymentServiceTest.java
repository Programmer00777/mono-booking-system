package com.mono.bookingsystem;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.service.CreatePaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class CreatePaymentServiceTest {

    private final CreatePaymentService createPaymentService;

    @Autowired
    public CreatePaymentServiceTest(CreatePaymentService createPaymentService) {
        this.createPaymentService = createPaymentService;
    }

    @Test
    void savesPaymentAndReturnsNonNullUUIDWithPaymentArgument() {
        Payment payment = new Payment("Karl Johnson", 320.0);
        Assertions.assertNull(payment.getId());
        createPaymentService.createPayment(payment);
        Assertions.assertNotNull(payment.getId());
    }

    @Test
    void savesPaymentAndReturnsNonNullUUIDWithFullNameAndAmountArguments() {
        String fullName = "John Doe";
        Double amount = 900.0;
        UUID uuid = createPaymentService.createPayment(fullName, amount);
        Assertions.assertNotNull(uuid);
    }
}
