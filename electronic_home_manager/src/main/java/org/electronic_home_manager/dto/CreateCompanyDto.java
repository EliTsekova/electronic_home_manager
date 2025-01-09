package org.electronic_home_manager.dto;


import java.time.LocalDate;

public class CreateCompanyDto {
    private String name;
    private LocalDate foundationDate;

    public CreateCompanyDto(String name, LocalDate foundationDate) {
        this.name = name;
        this.foundationDate = foundationDate;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(LocalDate foundationDate) {
        this.foundationDate = foundationDate;
    }
}
