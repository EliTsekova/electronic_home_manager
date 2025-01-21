package org.electronic_home_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for creating a new company.
 * Encapsulates the data required for creating a company and is used to transfer this data between application layers.
 */
public class CreateCompanyDto {

    private String email;
    private String address;
    private String phone;
    private Long id;

    /**
     * The name of the company.
     * Must be a non-null string with a length between 2 and 100 characters.
     */
    @NotNull(message = "Company name cannot be null.")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters.")
    private String name;

    /**
     * The foundation date of the company.
     * Must be a non-null value.
     */
    @NotNull(message = "Foundation date cannot be null.")
    private LocalDate foundationDate;

    /**
     * Default constructor for CreateCompanyDto.
     */
    public CreateCompanyDto() {}

    /**
     * Constructor for initializing CreateCompanyDto with specific values.
     *
     * @param name the name of the company.
     * @param address the address of the company.
     * @param phone the phone number of the company.
     * @param email the email of the company.
     */
    public CreateCompanyDto(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Gets the name of the company.
     *
     * @return the company name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the company.
     *
     * @return the company address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the company.
     *
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the phone number of the company.
     *
     * @return the company phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the company.
     *
     * @param phone the phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the email of the company.
     *
     * @return the company email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the company.
     *
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return the unique identifier (ID).
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the entity.
     *
     * @param id the unique identifier (ID).
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the foundation date of the company.
     *
     * @return the foundation date.
     */
    public LocalDate getFoundationDate() {
        return foundationDate;
    }

    /**
     * Sets the foundation date of the company.
     *
     * @param foundationDate the foundation date to set.
     */
    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }
}
