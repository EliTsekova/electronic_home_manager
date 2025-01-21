package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.BuildingDao;
import org.electronic_home_manager.dao.EmployeeDao;
import org.electronic_home_manager.dto.CreateBuildingDto;
import org.electronic_home_manager.dto.EmployeeDto;
import org.electronic_home_manager.entity.Apartment;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Employee;
import org.electronic_home_manager.entity.Resident;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BuildingService {
    private final BuildingDao buildingDao;
    private final EmployeeDao employeeDao;
    private final Map<Long, CreateBuildingDto> buildingCache = new ConcurrentHashMap<>();
    private ApartmentService apartmentService;

    public BuildingService(BuildingDao buildingDao, EmployeeDao employeeDao) {
        this.buildingDao = buildingDao;
        this.employeeDao = employeeDao;
    }

    // Retrieve all buildings as DTOs
    public List<CreateBuildingDto> getAllBuildings() {
        return buildingDao.findAll();
    }

    // Retrieve a building by ID
    public CreateBuildingDto getBuildingById(Long id) {
        return buildingCache.computeIfAbsent(id, key -> {
            Building building = buildingDao.findById(key);
            if (building == null) {
                throw new IllegalArgumentException("Building not found with ID: " + id);
            }
            return toDto(building);
        });
    }

    // Create a new building
    public void createBuilding(CreateBuildingDto buildingDto) {
        if (buildingDto == null) {
            throw new IllegalArgumentException("Building DTO cannot be null.");
        }

        Building building = toEntity(buildingDto);

        if (buildingDto.getEmployee() != null) {
            Employee employee = buildingDto.getEmployee();
            building.setEmployee(employee);
        }

        // Save the Building entity
        buildingDao.save(building);
    }



    // Update an existing building
    public void updateBuilding(CreateBuildingDto buildingDto) {
        Building existingBuildingDto = buildingDao.findById(buildingDto.getId());
        if (existingBuildingDto == null) {
            throw new IllegalArgumentException("Cannot update. Building not found with ID: " + buildingDto.getId());
        }
        buildingDao.update(buildingDto);
        buildingCache.put(buildingDto.getId(), buildingDto);
    }

    // Delete a building by ID
    public void deleteBuilding(Long id) {
        CreateBuildingDto buildingDto = getBuildingById(id);
        buildingDao.delete(id);
        buildingCache.remove(id);
    }

    // Assign a building to the least assigned employee
    public void assignBuildingToEmployee(CreateBuildingDto buildingDto) {
        EmployeeDto leastAssignedEmployee = employeeDao.findEmployeeWithLeastBuildings();
        if (leastAssignedEmployee != null) {
            buildingDto.setEmployee(toEntity(leastAssignedEmployee));
            buildingDao.update(buildingDto);
            buildingCache.put(buildingDto.getId(), buildingDto);
        } else {
            throw new IllegalStateException("No available employees!");
        }
    }

    // Filter buildings by employee ID
    public List<CreateBuildingDto> filterBuildingsByEmployee(Long employeeId) {
        return buildingDao.findAll().stream()
                .filter(buildingDto -> buildingDto.getEmployee() != null &&
                        buildingDto.getEmployee().getId().equals(employeeId))
                .collect(Collectors.toList());
    }

    // Get apartments in a building by building ID
    public List<Apartment> getApartmentsByBuildingId(Long buildingId) {
        Building building = buildingDao.findById(buildingId);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with ID: " + buildingId);
        }
        return building.getApartments();
    }

    // Get residents in a building by building ID
    public List<Resident> getResidentsByBuildingId(Long buildingId) {
        Building building = buildingDao.findById(buildingId);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with ID: " + buildingId);
        }
        return building.getApartments().stream()
                .flatMap(apartment -> apartment.getResidents().stream())
                .collect(Collectors.toList());
    }

    // Calculate the total monthly fees for a building
    public BigDecimal calculateBuildingFees(Long buildingId) {
        Building building = buildingDao.findById(buildingId);
        if (building == null) {
            throw new IllegalArgumentException("Building not found with ID: " + buildingId);
        }
        return building.getApartments().stream()
                .map(apartmentService::calculateMonthlyFee) // Returns BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum BigDecimal values
    }


    // Map employee to building counts
    public Map<EmployeeDto, Integer> getBuildingsCountByEmployee() {
        return employeeDao.findAll().stream()
                .collect(Collectors.toMap(
                        employee -> employee,
                        employee -> employee.getBuildings().size()
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


    // Helper method: Convert CreateBuildingDto to Building
    private Building toEntity(CreateBuildingDto dto) {
        Building building = new Building();
        building.setId(dto.getId());
        building.setAddress(dto.getAddress());
        building.setFloors(dto.getFloors());
        building.setCommonArea(dto.getCommonArea());
        return building;
    }
    public Employee toEntity(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        // You can set additional properties if needed
        return employee;
    }


}