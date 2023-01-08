package com.mono.bookingsystem.paymentsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;
    private String fullName;
    private Double amount;

    public Payment() {}

    public Payment(String fullName, Double amount) {
        this.fullName = fullName;
        this.amount = amount;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;

        if (!getId().equals(payment.getId())) return false;
        if (!getFullName().equals(payment.getFullName())) return false;
        return getAmount().equals(payment.getAmount());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFullName().hashCode();
        result = 31 * result + getAmount().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
