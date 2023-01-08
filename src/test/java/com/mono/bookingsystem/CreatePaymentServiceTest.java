package com.mono.bookingsystem;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreatePaymentServiceTest {

    private final PaymentRepository paymentRepository;

    @Autowired
    public CreatePaymentServiceTest(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Test
    void savesPaymentAndReturnsNonNullUUID() {
        Payment payment = new Payment("Karl Johnson", 320.0);
        Assertions.assertNull(payment.getId());
        paymentRepository.save(payment);
        Assertions.assertNotNull(payment.getId());
    }
}
