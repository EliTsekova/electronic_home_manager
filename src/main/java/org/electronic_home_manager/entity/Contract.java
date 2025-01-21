package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Represents a contract between a company and a building, managed by an employee.
 * Contracts define the relationship and obligations for building maintenance services.
 */
@Entity
@Table(name = "contract")
public class Contract extends BaseEntity {

    /**
     * The company associated with the contract.
     */
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    /**
     * The building covered under the contract.
     */
    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    /**
     * The employee responsible for the building in this contract.
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /**
     * The start date of the contract.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * The end date of the contract. Can be null if the contract is ongoing.
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * Indicates whether the contract is active.
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    /**
     * Default constructor for the Contract entity.
     */
    public Contract() {}

    /**
     * Constructs a Contract entity with specified parameters.
     *
     * @param company    The company associated with the contract.
     * @param building   The building covered under the contract.
     * @param employee   The employee responsible for the building.
     * @param startDate  The start date of the contract.
     * @param endDate    The end date of the contract (optional).
     */
    public Contract(Company company, Building building, Employee employee, LocalDate startDate, LocalDate endDate) {
        this.company = company;
        this.building = building;
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = true;
    }

    // Getters and Setters

    /**
     * Retrieves the company associated with the contract.
     *
     * @return The company entity.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company associated with the contract.
     *
     * @param company The company entity.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Retrieves the building covered under the contract.
     *
     * @return The building entity.
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the building covered under the contract.
     *
     * @param building The building entity.
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Retrieves the employee responsible for the building.
     *
     * @return The employee entity.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee responsible for the building.
     *
     * @param employee The employee entity.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Retrieves the start date of the contract.
     *
     * @return The start date as a LocalDate.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the contract.
     *
     * @param startDate The start date as a LocalDate.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Retrieves the end date of the contract.
     *
     * @return The end date as a LocalDate, or null if the contract is ongoing.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the contract.
     *
     * @param endDate The end date as a LocalDate, or null if the contract is ongoing.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Checks if the contract is active.
     *
     * @return True if the contract is active, false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the active status of the contract.
     *
     * @param active True to mark the contract as active, false otherwise.
     */
    public void setActive(boolean active) {
        isActive = active;
    }
}
