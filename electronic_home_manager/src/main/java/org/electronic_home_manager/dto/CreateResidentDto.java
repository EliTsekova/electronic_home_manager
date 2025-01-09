package org.electronic_home_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateResidentDto {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Positive
    private int age;

    @NotNull
    private boolean usesElevator;

    @NotNull
    private int apartmentId;

    public CreateResidentDto(String name, int age, boolean usesElevator, int apartmentId) {
        this.name = name;
        this.age = age;
        this.usesElevator = usesElevator;
        this.apartmentId = apartmentId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isUsesElevator() {
        return usesElevator;
    }

    public void setUsesElevator(boolean usesElevator) {
        this.usesElevator = usesElevator;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }
}
