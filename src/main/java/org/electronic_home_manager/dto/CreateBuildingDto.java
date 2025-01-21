package org.electronic_home_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.electronic_home_manager.entity.Employee;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for creating a new building.
 * This class encapsulates the data required for creating a building
 * and is used to transfer this data between the application layers.
 */
public class CreateBuildingDto {

    private Employee employee;
    private Long id;

    @NotNull(message = "Address cannot be null.")
    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters.")
    private String address;

    @Positive(message = "Number of floors must be a positive value.")
    private int floors;

    @Positive(message = "Common area must be a positive value.")
    private BigDecimal commonArea;

    @Positive(message = "Company ID must be a positive value.")
    private Long companyId;

    /**
     * Default constructor for CreateBuildingDto.
     */
    public CreateBuildingDto() {}

    /**
     * Constructor with all fields for CreateBuildingDto.
     *
     * @param id the unique identifier of the building.
     * @param address the address of the building.
     * @param floors the number of floors in the building.
     * @param commonArea the common area of the building.
     * @param companyId the ID of the company associated with the building.
     */
    public CreateBuildingDto(Long id, String address, int floors, BigDecimal commonArea, Long companyId) {
        this.id = id;
        this.address = address;
        this.floors = floors;
        this.commonArea = commonArea;
        this.companyId = companyId;
    }

    /**
     * Gets the address of the building.
     *
     * @return the address of the building.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the building.
     *
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the number of floors in the building.
     *
     * @return the number of floors.
     */
    public int getFloors() {
        return floors;
    }

    /**
     * Sets the number of floors in the building.
     *
     * @param floors the number of floors to set.
     */
    public void setFloors(int floors) {
        this.floors = floors;
    }

    /**
     * Gets the common area of the building.
     *
     * @return the common area as a BigDecimal.
     */
    public BigDecimal getCommonArea() {
        return commonArea;
    }

    /**
     * Sets the common area of the building.
     *
     * @param commonArea the common area to set.
     */
    public void setCommonArea(BigDecimal commonArea) {
        this.commonArea = commonArea;
    }

    /**
     * Gets the ID of the associated company.
     *
     * @return the company ID.
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Sets the ID of the associated company.
     *
     * @param companyId the company ID to set.
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Gets the employee associated with the building.
     *
     * @return the associated employee.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee associated with the building.
     *
     * @param employee the employee to associate with the building.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Gets the unique identifier of the building.
     *
     * @return the building ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the building.
     *
     * @param id the building ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Converts an EmployeeDto to an Employee entity.
     *
     * @param employeeDto the EmployeeDto to convert.
     * @return the Employee entity or null if the DTO is null.
     */
    public Employee toEntity(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        return employee;
    }
}
