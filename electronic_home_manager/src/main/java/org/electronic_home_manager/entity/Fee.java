package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "fee")
public class Fee extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(nullable = false)
    @Positive
    private double amount;

    @Column(name = "payment_date", nullable = false)
    @NotNull
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


    public Fee() {}

    public Fee(Apartment apartment, Building building, double amount, LocalDate paymentDate, Employee employee) {
        this.apartment = apartment;
        this.building = building;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.employee = employee;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double calculateTotalFee() {
        double baseFee = this.amount;

        if (apartment != null) {
            for (Resident resident : apartment.getResidents()) {
                baseFee += resident.calculateElevatorFee();
                baseFee += calculatePetFee();
            }
        }

        return baseFee;
    }

    public double calculatePetFee() {
        return apartment != null && apartment.isHasPet() ? 10.0 : 0.0;
    }

}
