package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a fee associated with an apartment, building, company, and optionally an employee.
 * The fee records payment information for building maintenance or related services.
 */
@Entity
@Table(name = "fee")
public class Fee extends BaseEntity {

    /**
     * The apartment associated with the fee.
     */
    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    /**
     * The building associated with the fee.
     */
    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    /**
     * The amount of the fee.
     */
    @Column(nullable = false)
    @Positive
    private BigDecimal amount;

    /**
     * The date the fee was paid.
     */
    @Column(name = "payment_date", nullable = false)
    @NotNull
    private LocalDate paymentDate;

    /**
     * The employee who processed or is responsible for the fee (optional).
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * The company associated with the fee.
     */
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    /**
     * Default constructor for the Fee entity.
     */
    public Fee() {}

    /**
     * Constructs a Fee entity with the specified attributes.
     *
     * @param apartment   The apartment associated with the fee.
     * @param building    The building associated with the fee.
     * @param amount      The amount of the fee.
     * @param paymentDate The date the fee was paid.
     * @param employee    The employee responsible for the fee.
     */
    public Fee(Apartment apartment, Building building, BigDecimal amount, LocalDate paymentDate, Employee employee) {
        this.apartment = apartment;
        this.building = building;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.employee = employee;
    }

    // Getters and Setters

    /**
     * Retrieves the apartment associated with the fee.
     *
     * @return The apartment entity.
     */
    public Apartment getApartment() {
        return apartment;
    }

    /**
     * Sets the apartment associated with the fee.
     *
     * @param apartment The apartment entity to set.
     */
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    /**
     * Retrieves the building associated with the fee.
     *
     * @return The building entity.
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the building associated with the fee.
     *
     * @param building The building entity to set.
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Retrieves the amount of the fee.
     *
     * @return The fee amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the fee.
     *
     * @param amount The fee amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the date the fee was paid.
     *
     * @return The payment date.
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the date the fee was paid.
     *
     * @param paymentDate The payment date to set.
     */
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Retrieves the employee responsible for the fee.
     *
     * @return The employee entity.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee responsible for the fee.
     *
     * @param employee The employee entity to set.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Retrieves the company associated with the fee.
     *
     * @return The company entity.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company associated with the fee.
     *
     * @param company The company entity to set.
     */
    public void setCompany(Company company) {
        this.company = company;
    }
}
