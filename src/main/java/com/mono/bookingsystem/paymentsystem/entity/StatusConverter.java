package com.mono.bookingsystem.paymentsystem.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.getStatusCode();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        return Stream.of(Status.values())
                .filter(status -> status.getStatusCode().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
