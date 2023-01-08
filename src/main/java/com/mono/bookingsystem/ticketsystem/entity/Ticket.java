package com.mono.bookingsystem.ticketsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;
    private String fullName;
    private Long tripId;

    public Ticket() {}

    public Ticket(String fullName, Long tripId) {
        this.fullName = fullName;
        this.tripId = tripId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;

        if (!getId().equals(ticket.getId())) return false;
        if (!getFullName().equals(ticket.getFullName())) return false;
        return getTripId().equals(ticket.getTripId());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFullName().hashCode();
        result = 31 * result + getTripId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", tripId=" + tripId +
                '}';
    }
}
