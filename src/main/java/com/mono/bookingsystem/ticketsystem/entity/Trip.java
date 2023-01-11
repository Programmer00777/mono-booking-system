package com.mono.bookingsystem.ticketsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Trip {

    @Id
    @GeneratedValue
    private UUID id;
    private String fromPlace; // "Place" post-fixed in here because of reserved SQL keyword "from"
    private String toPlace;
    private LocalDateTime date;
    private Double price;
    private Integer available;

    public Trip(String fromPlace,
                String toPlace,
                LocalDateTime date,
                Double price,
                Integer available) {
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.date = date;
        this.price = price;
        this.available = available;
    }

    public Trip() {}

    public UUID getId() {
        return id;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String to) {
        this.toPlace = to;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip trip)) return false;

        if (!getId().equals(trip.getId())) return false;
        if (!getFromPlace().equals(trip.getFromPlace())) return false;
        if (!getToPlace().equals(trip.getToPlace())) return false;
        if (!getDate().equals(trip.getDate())) return false;
        if (!getPrice().equals(trip.getPrice())) return false;
        return getAvailable().equals(trip.getAvailable());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFromPlace().hashCode();
        result = 31 * result + getToPlace().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getAvailable().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", from='" + fromPlace + '\'' +
                ", to='" + toPlace + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
