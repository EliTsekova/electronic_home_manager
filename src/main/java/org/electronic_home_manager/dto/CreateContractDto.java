package org.electronic_home_manager.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for creating a new contract.
 * This class encapsulates the necessary information for creating a contract
 * and is used to transfer data between different application layers.
 */
public class CreateContractDto {

    /**
     * The ID of the company associated with the contract.
     * Must be a non-null value.
     */
    @NotNull(message = "Company ID cannot be null.")
    private Long companyId;

    /**
     * The ID of the building associated with the contract.
     * Must be a non-null value.
     */
    @NotNull(message = "Building ID cannot be null.")
    private Long buildingId;

    /**
     * The ID of the employee assigned to the contract.
     * Must be a non-null value.
     */
    @NotNull(message = "Employee ID cannot be null.")
    private Long employeeId;

    /**
     * The start date of the contract.
     * Must be a non-null value.
     */
    @NotNull(message = "Start date cannot be null.")
    private LocalDate startDate;

    /**
     * The end date of the contract.
     * Optional; can be null if the contract is ongoing.
     */
    private LocalDate endDate;

    /**
     * Default constructor for CreateContractDto.
     */
    public CreateContractDto() {}

    /**
     * Parameterized constructor for CreateContractDto.
     *
     * @param companyId   the ID of the company.
     * @param buildingId  the ID of the building.
     * @param employeeId  the ID of the employee.
     * @param startDate   the start date of the contract.
     * @param endDate     the end date of the contract (optional).
     */
    public CreateContractDto(Long companyId, Long buildingId, Long employeeId, LocalDate startDate, LocalDate endDate) {
        this.companyId = companyId;
        this.buildingId = buildingId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters

    /**
     * Gets the company ID.
     *
     * @return the ID of the company.
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Sets the company ID.
     *
     * @param companyId the ID of the company.
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Gets the building ID.
     *
     * @return the ID of the building.
     */
    public Long getBuildingId() {
        return buildingId;
    }

    /**
     * Sets the building ID.
     *
     * @param buildingId the ID of the building.
     */
    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * Gets the employee ID.
     *
     * @return the ID of the employee.
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee ID.
     *
     * @param employeeId the ID of the employee.
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the start date of the contract.
     *
     * @return the start date of the contract.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the contract.
     *
     * @param startDate the start date of the contract.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the contract.
     *
     * @return the end date of the contract, or null if the contract is ongoing.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the contract.
     *
     * @param endDate the end date of the contract.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
