package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a building entity in the system.
 * Each building is associated with a company, an employee, and a list of apartments.
 */
@Entity
@Table(name = "building")
public class Building extends BaseEntity {

    /**
     * The address of the building.
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * The number of floors in the building.
     */
    @Column(name = "floors")
    private int floors;

    /**
     * The total common area (shared space) of the building in square meters.
     */
    @Column(name = "common_area")
    private BigDecimal commonArea;

    /**
     * The list of fees associated with the building.
     */
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fee> fees;

    /**
     * The contract associated with the building.
     */
    @OneToOne(mappedBy = "building")
    private Contract contract;

    /**
     * The company managing the building.
     */
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * The employee assigned to manage the building.
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * The list of apartments within the building.
     */
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartments;

    /**
     * Default constructor for the Building entity.
     */
    public Building() {}

    /**
     * Constructs a Building entity with specified parameters.
     *
     * @param address    The address of the building.
     * @param floors     The number of floors in the building.
     * @param commonArea The common area of the building.
     * @param company    The company managing the building.
     */
    public Building(String address, int floors, BigDecimal commonArea, Company company) {
        this.address = address;
        this.floors = floors;
        this.commonArea = commonArea;
        this.company = company;
    }

    // Getters and Setters

    /**
     * Retrieves the address of the building.
     *
     * @return The address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the building.
     *
     * @param address The new address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the number of floors in the building.
     *
     * @return The number of floors.
     */
    public int getFloors() {
        return floors;
    }

    /**
     * Sets the number of floors in the building.
     *
     * @param floors The new number of floors.
     */
    public void setFloors(int floors) {
        this.floors = floors;
    }

    /**
     * Retrieves the common area of the building.
     *
     * @return The common area.
     */
    public BigDecimal getCommonArea() {
        return commonArea;
    }

    /**
     * Sets the common area of the building.
     *
     * @param commonArea The new common area.
     */
    public void setCommonArea(BigDecimal commonArea) {
        this.commonArea = commonArea;
    }

    /**
     * Retrieves the company managing the building.
     *
     * @return The company.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company managing the building.
     *
     * @param company The new company.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Retrieves the employee managing the building.
     *
     * @return The employee.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee managing the building.
     *
     * @param employee The new employee.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Retrieves the list of apartments in the building.
     *
     * @return The list of apartments.
     */
    public List<Apartment> getApartments() {
        return apartments;
    }

    /**
     * Sets the list of apartments in the building.
     *
     * @param apartments The new list of apartments.
     */
    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }
}
