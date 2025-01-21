package org.electronic_home_manager.entity;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents an employee in a company responsible for managing buildings.
 */
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    /**
     * The name of the employee.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The phone number of the employee.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * The email address of the employee.
     */
    @Column(name = "email")
    private String email;

    /**
     * The company to which the employee belongs.
     */
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    /**
     * The list of buildings managed by the employee.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Building> buildings;

    /**
     * Default constructor for the Employee entity.
     */
    public Employee() {}

    /**
     * Constructs an Employee entity with the specified attributes.
     *
     * @param name    The name of the employee.
     * @param phone   The phone number of the employee.
     * @param email   The email address of the employee.
     * @param company The company the employee belongs to.
     */
    public Employee(String name, String phone, String email, Company company) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
    }

    // Getters and Setters

    /**
     * Retrieves the name of the employee.
     *
     * @return The employee's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name The name to set for the employee.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the phone number of the employee.
     *
     * @return The employee's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the employee.
     *
     * @param phone The phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieves the email address of the employee.
     *
     * @return The employee's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the employee.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the company to which the employee belongs.
     *
     * @return The company entity.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company to which the employee belongs.
     *
     * @param company The company entity to set.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Retrieves the list of buildings managed by the employee.
     *
     * @return A list of building entities.
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * Sets the list of buildings managed by the employee.
     *
     * @param buildings The list of building entities to set.
     */
    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
}
