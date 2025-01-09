package org.electronic_home_manager.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateContractDto {

    @NotNull
    private int companyId;

    @NotNull
    private int buildingId;

    @NotNull
    private int employeeId;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    public CreateContractDto(int companyId, int buildingId, int employeeId, LocalDate startDate, LocalDate endDate) {
        this.companyId = companyId;
        this.buildingId = buildingId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
