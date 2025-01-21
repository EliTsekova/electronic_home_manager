package org.electronic_home_manager.dto;

import jakarta.validation.constraints.Positive;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Resident;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for Apartment entity.
 * This class is used to encapsulate data related to an apartment
 * and is primarily used for transferring data between layers in the application.
 */
public class ApartmentDto {

    /**
     * Unique identifier for the apartment.
     * Must be a positive value.
     */
    @Positive(message = "ID must be a positive value.")
    private Long id;

    /**
     * Apartment number within the building.
     * Must be a positive integer.
     */
    @Positive(message = "Apartment number must be a positive value.")
    private int number;

    /**
     * Total area of the apartment in square meters.
     * Must be a positive number.
     */
    @Positive(message = "Area must be a positive value.")
    private BigDecimal area;

    /**
     * Indicates if the apartment has a pet.
     */
    private boolean hasPet;

    /**
     * ID of the building to which the apartment belongs.
     * Must be a positive value.
     */
    @Positive(message = "Building ID must be a positive value.")
    private Long buildingId;


    /**
     * Default constructor for ApartmentDto.
     */
    public ApartmentDto() {}

    /**
     * Parameterized constructor for ApartmentDto.
     *
     * @param id         the unique identifier for the apartment.
     * @param number     the apartment number.
     * @param area       the area of the apartment in square meters.
     * @param hasPet     indicates if the apartment has a pet.
     * @param buildingId the ID of the building the apartment belongs to.
     */
    public ApartmentDto(Long id, int number, BigDecimal area, boolean hasPet, Long buildingId) {
        this.id = id;
        this.number = number;
        this.area = area;
        this.hasPet = hasPet;
        this.buildingId = buildingId;
    }

    // Getters and Setters

    /**
     * Gets the ID of the apartment.
     *
     * @return the ID of the apartment.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the apartment.
     *
     * @param id the ID of the apartment.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the apartment number.
     *
     * @return the apartment number.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the apartment number.
     *
     * @param number the apartment number.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Gets the area of the apartment.
     *
     * @return the area of the apartment.
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * Sets the area of the apartment.
     *
     * @param area the area of the apartment.
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * Checks if the apartment has a pet.
     *
     * @return true if the apartment has a pet, false otherwise.
     */
    public boolean isHasPet() {
        return hasPet;
    }

    /**
     * Sets whether the apartment has a pet.
     *
     * @param hasPet true if the apartment has a pet, false otherwise.
     */
    public void setHasPet(boolean hasPet) {
        this.hasPet = hasPet;
    }

    /**
     * Gets the ID of the building to which the apartment belongs.
     *
     * @return the building ID.
     */
    public Long getBuildingId() {
        return buildingId;
    }

    /**
     * Sets the ID of the building to which the apartment belongs.
     *
     * @param buildingId the building ID.
     */
    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
    /**
     * Sets the owner of the apartment.
     * @param owner Owner of the apartment
     */
    public void setOwner(Resident owner) {
    }
    public void setResidents(List<Resident> residents) {
        List<?> residents1 = residents != null ? residents : new ArrayList<>();
    }
    /**
     * Sets the building where the apartment is located.
     * @param building Building (cannot be null)
     */
    public void setBuilding(Building building) {
        if (building == null) {
            throw new IllegalArgumentException("Building cannot be null.");
        }
    }
}
