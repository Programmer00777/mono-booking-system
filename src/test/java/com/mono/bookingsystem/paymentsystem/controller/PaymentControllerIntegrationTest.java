package com.mono.bookingsystem.paymentsystem.controller;

import com.mono.bookingsystem.paymentsystem.dto.PaymentStatusDto;
import com.mono.bookingsystem.paymentsystem.entity.Status;
import com.mono.bookingsystem.paymentsystem.service.CreatePaymentService;
import com.mono.bookingsystem.paymentsystem.service.FetchPaymentStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
class PaymentControllerIntegrationTest {

//    @Autowired
//    private MockMvc mockMvc;

//    @MockBean
//    private FetchPaymentStatusService fetchPaymentStatusService;

//    @MockBean
//    private CreatePaymentService createPaymentService;

    @Test
    void getPaymentStatusList() throws Exception {
//        PaymentStatusDto statusDto1 =
//                new PaymentStatusDto(UUID.fromString("aabc4b59-ee92-490b-a878-7b100ec79973"), Status.DONE);
//        PaymentStatusDto statusDto2 =
//                new PaymentStatusDto(UUID.fromString("9a772fb1-191f-4aa9-ae5b-676b76a95ed9"), Status.FAILED);
//        PaymentStatusDto statusDto3 =
//                new PaymentStatusDto(UUID.fromString("e83d37fd-8bf2-4a11-9522-c72c5fb468f8"), Status.NEW);
//        List<PaymentStatusDto> statusDtoList = Arrays.asList(statusDto1, statusDto2, statusDto3);
//        when(fetchPaymentStatusService.fetchPaymentList()).thenReturn(statusDtoList);
//
//        mockMvc.perform(get("/payment/status/all"))
//                .andDo(print())
//                .andExpect(status().isOk());
    }

    @Test
    void getPaymentStatusById() {
    }

    @Test
    void createPayment() {
    }

    @Test
    void updatePaymentStatusById() {
    }
}