package org.electronic_home_manager;

import org.electronic_home_manager.dao.*;
import org.electronic_home_manager.dto.*;
import org.electronic_home_manager.entity.*;
import org.electronic_home_manager.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Initialize DAOs
        CompanyDao companyDao = new CompanyDao();
        BuildingDao buildingDao = new BuildingDao();
        ApartmentDao apartmentDao = new ApartmentDao();
        ResidentDao residentDao = new ResidentDao();
        EmployeeDao employeeDao = new EmployeeDao();
        FeeDao feeDao = new FeeDao();

        // Initialize Services
        EmployeeService employeeService = new EmployeeService(employeeDao, buildingDao);
        FeeService feeService = new FeeService(feeDao, apartmentDao, new FileExportService());
        BuildingService buildingService = new BuildingService(buildingDao, employeeDao);
        ApartmentService apartmentService = new ApartmentService(apartmentDao);

        try {
            // Step 1: Create a company
            CreateCompanyDto companyDto = new CreateCompanyDto("Domoupravitel Ltd", "Main Street", "5555555555", "info@domoupravitel.bg");
            companyDao.save(companyDto);
            System.out.println("Company created successfully.");

            // Step 2: Create an employee
            EmployeeDto employeeDto = new EmployeeDto(null, "Ivan Ivanov", "ivan.ivanov@domoupravitel.bg", "+359876543210");
            employeeService.addEmployee(employeeDto);
            EmployeeDto savedEmployee = employeeService.getAllEmployees().get(0); // Fetch the first saved employee
            System.out.println("Employee created successfully: " + savedEmployee.getName());

            // Step 3: Create a building and assign the employee
            CreateBuildingDto buildingDto = new CreateBuildingDto(null, "Residential Complex 1", 5, BigDecimal.valueOf(200.0), null);

            // Fetch the latest employee and assign to building
            Employee employee = convertToEntity(savedEmployee);
            buildingDto.setEmployee(employee);
            buildingService.createBuilding(buildingDto);

            CreateBuildingDto savedBuilding = buildingService.getAllBuildings().get(0); // Fetch the first saved building
            System.out.println("Building created successfully: " + savedBuilding.getAddress());

            // Step 4: Create apartments and assign to the building
            ApartmentDto apartment1 = new ApartmentDto(null, 101, BigDecimal.valueOf(50.0), true, savedBuilding.getId());
            ApartmentDto apartment2 = new ApartmentDto(null, 102, BigDecimal.valueOf(60.0), false, savedBuilding.getId());

            apartmentService.addApartment(apartment1, buildingDao.findById(savedBuilding.getId()));
            apartmentService.addApartment(apartment2, buildingDao.findById(savedBuilding.getId()));
            System.out.println("Apartments created successfully.");

            // Step 5: Create residents and assign to apartments
            Resident resident1 = new Resident("Anna Ivanova", 30, true, apartmentDao.findById(apartment1.getId()));
            Resident resident2 = new Resident("Georgi Georgiev", 45, false, apartmentDao.findById(apartment1.getId()));
            Resident resident3 = new Resident("Maria Petrova", 25, true, apartmentDao.findById(apartment2.getId()));

            residentDao.save(resident1);
            residentDao.save(resident2);
            residentDao.save(resident3);
            System.out.println("Residents created successfully.");

            // Step 6: Create fees for apartments
            feeService.createFee(new CreateFeeDto(apartment1.getId(), BigDecimal.valueOf(100.0), LocalDate.now()));
            feeService.createFee(new CreateFeeDto(apartment2.getId(), BigDecimal.valueOf(120.0), LocalDate.now()));
            System.out.println("Fees created successfully.");

            // Step 7: Export fees to file
            feeService.exportPaidFees("fees_report.txt");
            System.out.println("Fees exported successfully to 'fees_report.txt'.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    private static Employee convertToEntity(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null; // Handle null cases gracefully
        }

        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        return employee;
    }
}
