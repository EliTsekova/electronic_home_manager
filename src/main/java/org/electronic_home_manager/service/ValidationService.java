package org.electronic_home_manager.service;

import org.electronic_home_manager.entity.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^\\+?[0-9]{7,15}$";
    private static final String ADDRESS_REGEX = "^[A-Za-z0-9,.\\s]+$";
    private static final String NAME_REGEX = "^[A-Za-z\\s]+$";

    public boolean validateCompany(Company company) {
        return company.getName() != null && !company.getName().isEmpty() &&
                validateEmail(company.getEmail()) &&
                validatePhone(company.getPhone());
    }

    public boolean validateEmployee(Employee employee) {
        return employee.getName() != null && !employee.getName().isEmpty() &&
                validateEmail(employee.getEmail());
    }

    public boolean validateBuilding(Building building, List<Building> existingBuildings) {
        return building.getAddress() != null && Pattern.matches(ADDRESS_REGEX, building.getAddress()) &&
                validateUniqueAddress(building.getAddress(), existingBuildings);
    }

    public boolean validateApartment(int number, BigDecimal area, boolean hasPet) {
        return number > 0 && area.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean validateResident(Resident resident) {
        return resident.getName() != null && Pattern.matches(NAME_REGEX, resident.getName()) &&
                resident.getAge() > 0 && resident.getAge() <= 120;
    }

    private boolean validateUniqueAddress(String address, List<Building> existingBuildings) {
        return existingBuildings.stream().noneMatch(b -> b.getAddress().equalsIgnoreCase(address));
    }

    private boolean validateEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    private boolean validatePhone(String phone) {
        return Pattern.matches(PHONE_REGEX, phone);
    }

    public boolean validateApartment(Apartment apartment) {
        return apartment.getNumber() > 0 &&
                apartment.getArea() != null &&
                apartment.getArea().compareTo(BigDecimal.ZERO) > 0 &&
                apartment.getBuilding() != null;
    }
}

