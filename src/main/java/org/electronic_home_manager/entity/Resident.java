package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Represents a resident in an apartment building. A resident has attributes like name,
 * age, and whether they use the elevator. Each resident is associated with an apartment.
 */
@Entity
@Table(name = "resident")
public class Resident extends BaseEntity {

    /**
     * The name of the resident.
     */
    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String name;

    /**
     * The age of the resident. Must be between 0 and 120.
     */
    @Column(nullable = false)
    @Min(0)
    @Max(120)
    private int age;

    /**
     * Indicates whether the resident uses the elevator.
     */
    @Column(name = "uses_elevator")
    private boolean usesElevator;

    /**
     * The apartment associated with the resident.
     */
    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    /**
     * Default constructor for the Resident entity.
     */
    public Resident() {}

    /**
     * Constructs a Resident entity with the specified attributes.
     *
     * @param name         The name of the resident.
     * @param age          The age of the resident.
     * @param usesElevator Indicates whether the resident uses the elevator.
     * @param apartment    The apartment associated with the resident.
     */
    public Resident(String name, int age, boolean usesElevator, Apartment apartment) {
        this.name = name;
        this.age = age;
        this.usesElevator = usesElevator;
        this.apartment = apartment;
    }

    /**
     * Calculates the elevator usage fee for the resident.
     *
     * @return The fee amount. Returns 5.0 if the resident is over 7 years old and uses the elevator; otherwise, 0.0.
     */
    public double calculateElevatorFee() {
        return (age > 7 && usesElevator) ? 5.0 : 0.0;
    }

    // Getters and Setters

    /**
     * Retrieves the name of the resident.
     *
     * @return The name of the resident.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the resident.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the age of the resident.
     *
     * @return The age of the resident.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the resident.
     *
     * @param age The age to set. Must be between 0 and 120.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Checks if the resident uses the elevator.
     *
     * @return True if the resident uses the elevator, false otherwise.
     */
    public boolean isUsesElevator() {
        return usesElevator;
    }

    /**
     * Sets whether the resident uses the elevator.
     *
     * @param usesElevator True if the resident uses the elevator, false otherwise.
     */
    public void setUsesElevator(boolean usesElevator) {
        this.usesElevator = usesElevator;
    }

    /**
     * Retrieves the apartment associated with the resident.
     *
     * @return The apartment entity.
     */
    public Apartment getApartment() {
        return apartment;
    }

    /**
     * Sets the apartment associated with the resident.
     *
     * @param apartment The apartment to set.
     */
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
