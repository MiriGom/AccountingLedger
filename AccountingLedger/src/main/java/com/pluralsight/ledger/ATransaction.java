package com.pluralsight.ledger;

import java.time.LocalDate;
import java.time.LocalTime;

public class ATransaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public ATransaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor(){
        return vendor;
    }

    public double getAmount() {
        return amount;
    }
    @Override
    public String toString() {
        return String.format("%-12s %-12s %-25s %-20s $%-7.2f", date, time, description, vendor, amount);
    }
/*
    @Override
    public String toString() {
        return date + "|" +
                time + "|" +
                description + "|" +
                vendor + "|" +
                amount;
    }*/

}

