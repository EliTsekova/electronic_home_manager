package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a company entity in the system.
 * A company manages buildings, employs staff, and is responsible for collecting fees.
 */
@Entity
@Table(name = "company")
public class Company extends BaseEntity {

    /**
     * The name of the company.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The address of the company.
     */
    @Column(name = "address")
    private String address;

    /**
     * The contact phone number of the company.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * The contact email of the company.
     */
    @Column(name = "email")
    private String email;

    /**
     * The list of fees associated with this company.
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fee> fees;

    /**
     * The list of buildings managed by the company.
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Building> buildings;

    /**
     * The list of employees working for the company.
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    /**
     * Default constructor for the Company entity.
     */
    public Company() {}

    /**
     * Constructs a Company entity with specified parameters.
     *
     * @param name    The name of the company.
     * @param address The address of the company.
     * @param phone   The contact phone number of the company.
     * @param email   The contact email of the company.
     */
    public Company(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters

    /**
     * Retrieves the name of the company.
     *
     * @return The company name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     *
     * @param name The new name of the company.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the address of the company.
     *
     * @return The company address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the company.
     *
     * @param address The new address of the company.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the contact phone number of the company.
     *
     * @return The phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the contact phone number of the company.
     *
     * @param phone The new phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieves the contact email of the company.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the contact email of the company.
     *
     * @param email The new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the list of buildings managed by the company.
     *
     * @return The list of buildings.
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * Sets the list of buildings managed by the company.
     *
     * @param buildings The new list of buildings.
     */
    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    /**
     * Retrieves the list of employees working for the company.
     *
     * @return The list of employees.
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees working for the company.
     *
     * @param employees The new list of employees.
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
