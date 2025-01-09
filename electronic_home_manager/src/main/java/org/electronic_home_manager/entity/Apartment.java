package org.electronic_home_manager.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "apartment")
public class Apartment extends BaseEntity {

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "area")
    private double area;

    @Column(name = "has_pet")
    private boolean hasPet;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fee> fees;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resident> residents;


    public Apartment() {}

    public Apartment(int number, double area, boolean hasPet, Building building) {
        this.number = number;
        this.area = area;
        this.hasPet = hasPet;
        this.building = building;
    }

    // Getters and Setters
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }
}

