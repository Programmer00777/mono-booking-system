package com.mono.bookingsystem.ticketsystem.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private UUID id;
    private String fullName;
    private UUID tripId;

    private UUID paymentId;

    public Ticket() {}

    public Ticket(String fullName, UUID tripId, UUID paymentId) {
        this.fullName = fullName;
        this.tripId = tripId;
        this.paymentId = paymentId;
    }

    public Ticket(String fullName, UUID tripId) {
        this.fullName = fullName;
        this.tripId = tripId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UUID getTripId() {
        return tripId;
    }

    public void setTripId(UUID tripId) {
        this.tripId = tripId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;

        if (!getId().equals(ticket.getId())) return false;
        if (!getFullName().equals(ticket.getFullName())) return false;
        if (!getTripId().equals(ticket.getTripId())) return false;
        return getPaymentId().equals(ticket.getPaymentId());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFullName().hashCode();
        result = 31 * result + getTripId().hashCode();
        result = 31 * result + getPaymentId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", tripId=" + tripId +
                ", paymentId=" + paymentId +
                '}';
    }
}
