package com.mono.bookingsystem.paymentsystem.entity;

import com.mono.bookingsystem.paymentsystem.controller.PaymentController;
import com.mono.bookingsystem.paymentsystem.dto.PaymentStatusDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentStatusDtoModelAssembler {

    public EntityModel<PaymentStatusDto> toModel(PaymentStatusDto paymentStatusDto, UUID paymentId) {
        return EntityModel.of(paymentStatusDto,
                linkTo(methodOn(PaymentController.class).getPaymentStatusById(paymentId.toString())).withSelfRel(),
                linkTo(methodOn(PaymentController.class).getPaymentStatusList()).withRel("paymentStatusList"));
    }
}
