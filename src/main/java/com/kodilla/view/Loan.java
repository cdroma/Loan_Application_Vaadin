package com.kodilla.view;

import java.time.LocalDate;

public class Loan {
    private Long id;
    private double amount;
    private String currency;
    private LocalDate dueDate;
    private boolean isPaidOff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaidOff() {
        return isPaidOff;
    }

    public void setPaidOff(boolean paidOff) {
        isPaidOff = paidOff;
    }
}
