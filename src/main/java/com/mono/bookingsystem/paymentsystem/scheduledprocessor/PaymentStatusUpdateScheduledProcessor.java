package com.mono.bookingsystem.paymentsystem.scheduledprocessor;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.repository.PaymentRepository;
import com.mono.bookingsystem.paymentsystem.service.FetchPaymentStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class PaymentStatusUpdateScheduledProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentStatusUpdateScheduledProcessor.class);
    private final FetchPaymentStatusService fetchPaymentStatusService;
    private final PaymentRepository paymentRepository;
    private final RestTemplate template = new RestTemplate();
    private final Environment environment;

    @Autowired
    public PaymentStatusUpdateScheduledProcessor(FetchPaymentStatusService fetchPaymentStatusService,
                                                 PaymentRepository paymentRepository,
                                                 Environment environment) {
        this.fetchPaymentStatusService = fetchPaymentStatusService;
        this.paymentRepository = paymentRepository;
        this.environment = environment;
    }

    @Scheduled(fixedDelay = 3500)
    public void updatePaymentStatuses() {
        List<Payment> paymentList = fetchPaymentStatusService.getAllPayments();
        if (paymentList.size() > 0) {
            String generalAvailableUpdateURI =
                    String.format("http://localhost:%s/ticket/available/update/", environment.getProperty("local.server.port"));

            paymentList.forEach(payment -> {
                if (payment.getStatus().toString().equals("NEW")) {
                    fetchPaymentStatusService.updateStatusById(payment.getId());
                } else if (!payment.isProcessed() && payment.getStatus().toString().equals("FAILED")) {
                    LOGGER.warn("Payment with ID " + payment.getId() + " failed.");
                    payment.setProcessed(true);
                    paymentRepository.save(payment);
                    template.put(generalAvailableUpdateURI + payment.getId(), null);
                } else if (!payment.isProcessed()) {
                    LOGGER.info("Payment with ID " + payment.getId() + " done.");
                    payment.setProcessed(true);
                    paymentRepository.save(payment);
                }
            });
        }
    }
}
