package org.electronic_home_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for creating a new resident.
 * This class encapsulates the necessary information for creating a resident
 * and is used to transfer data between different application layers.
 */
public class CreateResidentDto {

    /**
     * The name of the resident.
     * Must be a non-null value with a length between 2 and 100 characters.
     */
    @NotNull(message = "Name cannot be null.")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
    private String name;

    /**
     * The age of the resident.
     * Must be a positive value.
     */
    @Positive(message = "Age must be a positive value.")
    private int age;

    /**
     * Indicates whether the resident uses the elevator.
     * Must be a non-null value.
     */
    @NotNull(message = "Elevator usage cannot be null.")
    private boolean usesElevator;

    /**
     * The ID of the apartment the resident is associated with.
     * Must be a non-null value.
     */
    @NotNull(message = "Apartment ID cannot be null.")
    private Long apartmentId;

    /**
     * Default constructor for CreateResidentDto.
     */
    public CreateResidentDto() {}

    /**
     * Parameterized constructor for CreateResidentDto.
     *
     * @param name          the name of the resident.
     * @param age           the age of the resident.
     * @param usesElevator  indicates if the resident uses the elevator.
     * @param apartmentId   the ID of the associated apartment.
     */
    public CreateResidentDto(String name, int age, boolean usesElevator, Long apartmentId) {
        this.name = name;
        this.age = age;
        this.usesElevator = usesElevator;
        this.apartmentId = apartmentId;
    }

    // Getters and Setters

    /**
     * Gets the name of the resident.
     *
     * @return the name of the resident.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the resident.
     *
     * @param name the name of the resident.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the resident.
     *
     * @return the age of the resident.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the resident.
     *
     * @param age the age of the resident.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets whether the resident uses the elevator.
     *
     * @return true if the resident uses the elevator, false otherwise.
     */
    public boolean isUsesElevator() {
        return usesElevator;
    }

    /**
     * Sets whether the resident uses the elevator.
     *
     * @param usesElevator true if the resident uses the elevator, false otherwise.
     */
    public void setUsesElevator(boolean usesElevator) {
        this.usesElevator = usesElevator;
    }

    /**
     * Gets the ID of the associated apartment.
     *
     * @return the apartment ID.
     */
    public Long getApartmentId() {
        return apartmentId;
    }

    /**
     * Sets the ID of the associated apartment.
     *
     * @param apartmentId the apartment ID.
     */
    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }
}
