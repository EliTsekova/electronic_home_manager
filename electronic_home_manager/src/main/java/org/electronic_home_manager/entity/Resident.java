package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "resident")
public class Resident extends BaseEntity {

    @Column(nullable = false)
    @Size(min = 2, max = 100)
    private String name;

    @Column(nullable = false)
    @Min(0)
    @Max(120)
    private int age;


    @Column(name = "uses_elevator")
    private boolean usesElevator;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    public Resident() {}

    public Resident(String name, int age, boolean usesElevator, Apartment apartment) {
        this.name = name;
        this.age = age;
        this.usesElevator = usesElevator;
        this.apartment = apartment;
    }

    public double calculateElevatorFee() {
        return (age > 7 && usesElevator) ? 5.0 : 0.0;
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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
