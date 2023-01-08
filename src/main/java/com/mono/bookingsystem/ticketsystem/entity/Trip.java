package com.mono.bookingsystem.ticketsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Trip {

    @Id
    @GeneratedValue
    private Long id;
    private String from;
    private String to;
    private Date date;
    private Double price;
    private Integer available;

    public Trip(String from,
                String to,
                Date date,
                Double price,
                Integer available) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.price = price;
        this.available = available;
    }

    public Trip() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
        if (!getFrom().equals(trip.getFrom())) return false;
        if (!getTo().equals(trip.getTo())) return false;
        if (!getDate().equals(trip.getDate())) return false;
        if (!getPrice().equals(trip.getPrice())) return false;
        return getAvailable().equals(trip.getAvailable());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFrom().hashCode();
        result = 31 * result + getTo().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + getAvailable().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", available=" + available +
                '}';
    }
}
