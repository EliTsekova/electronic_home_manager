package org.electronic_home_manager.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "building")
public class Building extends BaseEntity {

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "floors")
    private int floors;

    @Column(name = "common_area")
    private double commonArea;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fee> fees;
    @OneToOne(mappedBy = "building")
    private Contract contract;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apartment> apartments;

    public Building() {}

    public Building(String address, int floors, double commonArea, Company company) {
        this.address = address;
        this.floors = floors;
        this.commonArea = commonArea;
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

}
