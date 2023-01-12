package com.mono.bookingsystem.paymentsystem.controller;

import com.mono.bookingsystem.paymentsystem.dto.PaymentStatusDto;
import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.dto.PaymentStatusDtoModelAssembler;
import com.mono.bookingsystem.paymentsystem.exception.InvalidPaymentException;
import com.mono.bookingsystem.paymentsystem.service.CreatePaymentService;
import com.mono.bookingsystem.paymentsystem.service.FetchPaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {

    private final FetchPaymentStatusService fetchPaymentStatusService;
    private final CreatePaymentService createPaymentService;
    private final PaymentStatusDtoModelAssembler paymentStatusDtoModelAssembler;

    @Autowired
    public PaymentController(FetchPaymentStatusService fetchPaymentStatusService,
                             CreatePaymentService createPaymentService,
                             PaymentStatusDtoModelAssembler paymentStatusDtoModelAssembler) {
        this.fetchPaymentStatusService = fetchPaymentStatusService;
        this.createPaymentService = createPaymentService;
        this.paymentStatusDtoModelAssembler = paymentStatusDtoModelAssembler;
    }

    @GetMapping(path = "/status/all")
    public CollectionModel<EntityModel<PaymentStatusDto>> getPaymentStatusList() {
        List<EntityModel<PaymentStatusDto>> paymentList = fetchPaymentStatusService.fetchPaymentList()
                .stream().map(paymentStatusDtoModelAssembler::toModel).toList();

        return CollectionModel
                .of(paymentList, linkTo(methodOn(PaymentController.class).getPaymentStatusList()).withSelfRel());
    }

    @GetMapping(path = "/status/{paymentId}")
    public EntityModel<PaymentStatusDto> getPaymentStatusById(@PathVariable("paymentId") UUID id) {
        return paymentStatusDtoModelAssembler.toModel(fetchPaymentStatusService.fetchStatusWithId(id));
    }

    @PostMapping(path = "/create")
    public UUID createPayment(@RequestBody Payment payment) {
        if (payment.getFullName() == null || payment.getAmount() < 0.0) {
            throw new InvalidPaymentException("Invalid full name or amount");
        } else {
            return createPaymentService.createPayment(payment);
        }
    }

    @PutMapping(path = "/status/update/{paymentId}")
    public void updatePaymentStatusById(@PathVariable("paymentId") String paymentId) {
        fetchPaymentStatusService.updateStatusById(UUID.fromString(paymentId));
     }

     // @DeleteMapping...
}
