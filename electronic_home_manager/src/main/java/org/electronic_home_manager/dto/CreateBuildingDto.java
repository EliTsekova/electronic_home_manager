package org.electronic_home_manager.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateBuildingDto {

    @NotNull
    private String address;

    @Positive
    private int floors;

    @Positive
    private double commonArea;

    @NotNull
    private int companyId;

    public CreateBuildingDto(String address, int floors, double commonArea, int companyId) {
        this.address = address;
        this.floors = floors;
        this.commonArea = commonArea;
        this.companyId = companyId;
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public double getCommonArea() {
        return commonArea;
    }

    public void setCommonArea(double commonArea) {
        this.commonArea = commonArea;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}

