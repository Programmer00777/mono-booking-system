package com.mono.bookingsystem.paymentsystem.controller;

import com.mono.bookingsystem.paymentsystem.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerAcceptanceTest {

    @LocalServerPort
    private int randomServerPort;

    private RestTemplate restTemplate;
    private String url;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        url = "http://localhost:" + randomServerPort + "/payment";
    }

    @Test
    void shouldReturnNonNullUUIDOnPaymentCreation() {
        Payment payment = new Payment("John Doe", 400.0);
        ResponseEntity<UUID> responseEntity = restTemplate.postForEntity(url + "/create", payment, UUID.class);
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void shouldReturnThreeModelsOfPaymentStatus() {
        ResponseEntity<CollectionModel> entities =
                restTemplate.getForEntity(url + "/status/all", CollectionModel.class);
        assertEquals(3, Objects.requireNonNull(entities.getBody()).getContent().size());
    }
}
