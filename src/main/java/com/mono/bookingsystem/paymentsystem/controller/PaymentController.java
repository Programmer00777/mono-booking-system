package com.mono.bookingsystem.paymentsystem.controller;

import com.mono.bookingsystem.paymentsystem.dto.PaymentStatusDto;
import com.mono.bookingsystem.paymentsystem.entity.Payment;
import com.mono.bookingsystem.paymentsystem.entity.PaymentStatusDtoModelAssembler;
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
        List<EntityModel<PaymentStatusDto>> statusList = fetchPaymentStatusService.fetchStatusList()
                .stream().map(paymentStatusDtoEntry ->
                        paymentStatusDtoModelAssembler
                                .toModel(paymentStatusDtoEntry, paymentStatusDtoEntry.getPaymentId())).toList();

        return CollectionModel
                .of(statusList, linkTo(methodOn(PaymentController.class).getPaymentStatusList()).withSelfRel());
    }


    @GetMapping(path = "/status/{paymentId}")
    public EntityModel<PaymentStatusDto> getPaymentStatusById(@PathVariable("paymentId") String id) {
        PaymentStatusDto paymentStatusDto = fetchPaymentStatusService.fetchStatusWithId(UUID.fromString(id));
        return paymentStatusDtoModelAssembler.toModel(paymentStatusDto, UUID.fromString(id));
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
