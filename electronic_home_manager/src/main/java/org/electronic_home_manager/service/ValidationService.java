package org.electronic_home_manager.service;

import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.entity.Employee;

import java.util.regex.Pattern;

public class ValidationService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^\\+?[0-9]{7,15}$";

    public boolean validateCompany(Company company) {
        return company.getName() != null && !company.getName().isEmpty() &&
                validateEmail(company.getEmail()) &&
                validatePhone(company.getPhone());
    }

    public boolean validateEmployee(Employee employee) {
        return employee.getName() != null && !employee.getName().isEmpty() &&
                validateEmail(employee.getEmail());
    }

    private boolean validateEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    private boolean validatePhone(String phone) {
        return Pattern.matches(PHONE_REGEX, phone);
    }
}
