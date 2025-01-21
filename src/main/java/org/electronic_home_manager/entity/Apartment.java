package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an Apartment in a building.
 * An Apartment contains details about its number, area, residents,
 * ownership, and associated fees.
 */
@Entity
@Table(name = "apartment")
public class Apartment extends BaseEntity {

    @Column(name = "number", nullable = false)
    private int number; // Apartment number

    @Column(name = "area")
    private BigDecimal area; // Total area of the apartment

    @Column(name = "has_pet")
    private boolean hasPet; // Indicates if the apartment has a pet

    @Column(name = "floor", nullable = false)
    private int floor; // The floor where the apartment is located

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Resident owner; // Owner of the apartment

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fee> fees; // List of fees associated with the apartment

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building; // The building where the apartment is located

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resident> residents; // List of residents living in the apartment

    // Default constructor
    public Apartment() {}

    /**
     * Parameterized constructor for creating an Apartment instance.
     * @param number Apartment number (must be positive)
     * @param area Total area of the apartment (must be positive)
     * @param hasPet Indicates if the apartment has a pet
     * @param building The building where the apartment is located (cannot be null)
     */
    public Apartment(int number, BigDecimal area, boolean hasPet, Building building) {
        if (number <= 0) {
            throw new IllegalArgumentException("Apartment number must be positive.");
        }
        if (area == null || area.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Apartment area must be positive.");
        }
        if (building == null) {
            throw new IllegalArgumentException("Building cannot be null.");
        }

        this.number = number;
        this.area = area;
        this.hasPet = hasPet;
        this.building = building;
    }

    // Getters and Setters
    /**
     * Gets the apartment number.
     * @return Apartment number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the apartment number.
     * @param number Apartment number (must be positive)
     */
    public void setNumber(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Apartment number must be positive.");
        }
        this.number = number;
    }

    /**
     * Gets the total area of the apartment.
     * @return Apartment area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * Sets the total area of the apartment.
     * @param area Apartment area (must be positive)
     */
    public void setArea(BigDecimal area) {
        if (area == null || area.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Apartment area must be positive.");
        }
        this.area = area;
    }

    /**
     * Checks if the apartment has a pet.
     * @return True if the apartment has a pet, false otherwise
     */
    public boolean isHasPet() {
        return hasPet;
    }

    /**
     * Sets the pet status of the apartment.
     * @param hasPet True if the apartment has a pet, false otherwise
     */
    public void setHasPet(boolean hasPet) {
        this.hasPet = hasPet;
    }

    /**
     * Gets the building where the apartment is located.
     * @return Building of the apartment
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Sets the building where the apartment is located.
     * @param building Building (cannot be null)
     */
    public void setBuilding(Building building) {
        if (building == null) {
            throw new IllegalArgumentException("Building cannot be null.");
        }
        this.building = building;
    }

    /**
     * Gets the list of fees associated with the apartment.
     * @return List of fees
     */
    public List<Fee> getFees() {
        return fees != null ? fees : new ArrayList<>();
    }

    /**
     * Sets the list of fees associated with the apartment.
     * @param fees List of fees
     */
    public void setFees(List<Fee> fees) {
        this.fees = fees != null ? fees : new ArrayList<>();
    }

    /**
     * Gets the list of residents living in the apartment.
     * @return List of residents
     */
    public List<Resident> getResidents() {
        return residents != null ? residents : new ArrayList<>();
    }

    /**
     * Sets the list of residents living in the apartment.
     * @param residents List of residents
     */
    public void setResidents(List<Resident> residents) {
        this.residents = residents != null ? residents : new ArrayList<>();
    }

    /**
     * Gets the floor where the apartment is located.
     * @return Floor number
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Sets the floor where the apartment is located.
     * @param floor Floor number
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Gets the owner of the apartment.
     * @return Owner of the apartment
     */
    public Resident getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the apartment.
     * @param owner Owner of the apartment
     */
    public void setOwner(Resident owner) {
        this.owner = owner;
    }
}
