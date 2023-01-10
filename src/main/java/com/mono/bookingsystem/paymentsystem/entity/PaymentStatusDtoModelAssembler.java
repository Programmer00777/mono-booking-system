package com.mono.bookingsystem.paymentsystem.entity;

import com.mono.bookingsystem.paymentsystem.controller.PaymentController;
import com.mono.bookingsystem.paymentsystem.dto.PaymentStatusDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentStatusDtoModelAssembler
implements RepresentationModelAssembler<PaymentStatusDto, EntityModel<PaymentStatusDto>> {

    @Override
    public EntityModel<PaymentStatusDto> toModel(PaymentStatusDto paymentStatusDto) {
        return EntityModel.of(paymentStatusDto,
                linkTo(methodOn(PaymentController.class).getPaymentStatusById(paymentStatusDto.getPaymentId())).withSelfRel(),
                linkTo(methodOn(PaymentController.class).getPaymentStatusList()).withSelfRel());
    }
}
