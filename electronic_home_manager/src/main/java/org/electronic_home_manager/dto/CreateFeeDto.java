package org.electronic_home_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class CreateFeeDto {

    @NotNull
    private int apartmentId;

    @NotNull
    private int buildingId;

    @Positive
    private double amount;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    private int employeeId;

    public CreateFeeDto(int apartmentId, int buildingId, double amount, LocalDate paymentDate, int employeeId) {
        this.apartmentId = apartmentId;
        this.buildingId = buildingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.employeeId = employeeId;
    }

    // Getters and Setters
    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
