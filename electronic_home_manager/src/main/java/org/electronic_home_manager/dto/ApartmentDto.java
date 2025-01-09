package org.electronic_home_manager.dto;

public class ApartmentDto {
    private int id;
    private int number;
    private double area;
    private boolean hasPet;
    private int buildingId;

    public ApartmentDto() {}

    public ApartmentDto(int id, int number, double area, boolean hasPet, int buildingId) {
        this.id = id;
        this.number = number;
        this.area = area;
        this.hasPet = hasPet;
        this.buildingId = buildingId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public boolean isHasPet() {
        return hasPet;
    }

    public void setHasPet(boolean hasPet) {
        this.hasPet = hasPet;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
}
