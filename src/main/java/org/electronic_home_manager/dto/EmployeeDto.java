package org.electronic_home_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.electronic_home_manager.entity.Building;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Employee.
 * This class encapsulates the necessary information for transferring
 * employee data between different application layers.
 */
public class EmployeeDto {
    private  List<Building> buildings;

    /**
     * The unique identifier of the employee.
     * Must be a non-null value.
     */
    @NotNull(message = "Employee ID cannot be null.")
    private Long id;

    /**
     * The name of the employee.
     * Must be a non-null value with a length between 2 and 100 characters.
     */
    @NotNull(message = "Name cannot be null.")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
    private String name;

    /**
     * The email address of the employee.
     * Must be a valid email format and non-null value.
     */
    @NotNull(message = "Email cannot be null.")
    @Email(message = "Invalid email format.")
    private String email;

    /**
     * The phone number of the employee.
     * Must have a length between 7 and 15 characters.
     */
    @Size(min = 7, max = 15, message = "Phone number must be between 7 and 15 characters.")
    private String phone;

    /**
     * Default constructor for EmployeeDto.
     */
    public EmployeeDto() {}

    /**
     * Parameterized constructor for EmployeeDto.
     *
     * @param id    the unique identifier of the employee.
     * @param name  the name of the employee.
     * @param email the email address of the employee.
     * @param phone the phone number of the employee.
     */
    public EmployeeDto(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters

    /**
     * Gets the unique identifier of the employee.
     *
     * @return the employee ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the employee.
     *
     * @param id the employee ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the employee.
     *
     * @return the employee's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name the employee's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the employee.
     *
     * @return the employee's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the employee.
     *
     * @param email the employee's email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the employee.
     *
     * @return the employee's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the employee.
     *
     * @param phone the employee's phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
