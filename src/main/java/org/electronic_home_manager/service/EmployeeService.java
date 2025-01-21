package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.BuildingDao;
import org.electronic_home_manager.dao.EmployeeDao;
import org.electronic_home_manager.dto.CreateBuildingDto;
import org.electronic_home_manager.dto.EmployeeDto;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeDao employeeDao;
    private final BuildingDao buildingDao;

    public EmployeeService(EmployeeDao employeeDao, BuildingDao buildingDao) {
        this.employeeDao = employeeDao;
        this.buildingDao = buildingDao;
    }

    // Retrieve all employees as DTOs
    public List<EmployeeDto> getAllEmployees() {
        return employeeDao.findAll();
    }

    // Retrieve a single employee by ID as a DTO
    public EmployeeDto getEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    // Add a new employee using DTO
    public void addEmployee(EmployeeDto employeeDto) {
        employeeDao.save(employeeDto);
    }


    public void updateEmployee(EmployeeDto employeeDto) {
        employeeDao.update(employeeDto);
    }

    // Delete an employee by ID
    public void deleteEmployee(Long id) {
        EmployeeDto employeeDto = employeeDao.findById(id);
        if (employeeDto == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        employeeDao.delete(id);
    }

    public void reassignBuildings(EmployeeDto leavingEmployeeDto) {
        Employee leavingEmployee = employeeDao.toEntity(leavingEmployeeDto);
        List<Building> buildings = new ArrayList<>(leavingEmployee.getBuildings()); // Copy to avoid modification during iteration
        List<Employee> remainingEmployees = employeeDao.findAll().stream()
                .map(employeeDao::toEntity)
                .filter(emp -> !emp.getId().equals(leavingEmployee.getId()))
                .toList();

        if (!remainingEmployees.isEmpty()) {
            for (Building building : buildings) {
                Employee leastLoadedEmployee = remainingEmployees.stream()
                        .min(Comparator.comparingInt(e -> e.getBuildings().size()))
                        .orElseThrow(() -> new IllegalStateException("No available employees!"));

                building.setEmployee(leastLoadedEmployee);
                leastLoadedEmployee.getBuildings().add(building);
                buildingDao.update(toDto(building));
            }
            // Clear buildings from the leaving employee
            leavingEmployee.getBuildings().clear();
        } else {
            throw new IllegalStateException("No employees available to reassign buildings!");
        }
    }


    // Sort employees by name
    public List<EmployeeDto> sortEmployeesByName() {
        return employeeDao.findAll().stream()
                .sorted(Comparator.comparing(EmployeeDto::getName))
                .collect(Collectors.toList());
    }

    // Sort employees by building count
    public List<EmployeeDto> sortEmployeesByBuildingCount() {
        return employeeDao.findAll().stream()
                .sorted(Comparator.comparingInt(employeeDto -> employeeDao.toEntity(employeeDto).getBuildings().size()))
                .collect(Collectors.toList());
    }

    // Retrieve a map of employees to their assigned buildings
    public Map<EmployeeDto, List<Building>> getBuildingsByEmployee() {
        return employeeDao.findAll().stream()
                .collect(Collectors.toMap(
                        employeeDto -> employeeDto,
                        employeeDto -> employeeDao.toEntity(employeeDto).getBuildings()
                ));
    }

    // Retrieve a map of employees to their building counts
    public Map<EmployeeDto, Integer> getBuildingCountByEmployee() {
        return employeeDao.findAll().stream()
                .collect(Collectors.toMap(
                        employeeDto -> employeeDto,
                        employeeDto -> employeeDao.toEntity(employeeDto).getBuildings().size()
                ));
    }

    // Retrieve a map of employees to their assigned buildings, filtered by company
    public Map<EmployeeDto, List<Building>> getBuildingsByEmployeeInCompany(Long companyId) {
        return employeeDao.findAll().stream()
                .filter(employeeDto -> {
                    Employee employee = employeeDao.toEntity(employeeDto);
                    return employee.getCompany() != null && Objects.equals(employee.getCompany().getId(), companyId);
                })
                .collect(Collectors.toMap(
                        employeeDto -> employeeDto,
                        employeeDto -> employeeDao.toEntity(employeeDto).getBuildings()
                ));
    }
    private CreateBuildingDto toDto(Building building) {
        return new CreateBuildingDto(
                building.getId(),
                building.getAddress(),
                building.getFloors(),
                building.getCommonArea(),
                building.getCompany() != null ? building.getCompany().getId() : null
        );
    }

}
