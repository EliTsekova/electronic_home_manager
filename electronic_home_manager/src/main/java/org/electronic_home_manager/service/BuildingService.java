package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.BuildingDao;
import org.electronic_home_manager.dao.EmployeeDao;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class BuildingService {
    private final BuildingDao buildingDao;
    private final EmployeeDao employeeDao;

    public BuildingService(BuildingDao buildingDao, EmployeeDao employeeDao) {
        this.buildingDao = buildingDao;
        this.employeeDao = employeeDao;
    }

    public List<Building> getAllBuildings() {
        return buildingDao.findAll();
    }

    public Building getBuildingById(int id) {
        return buildingDao.findById(id);
    }

    public void createBuilding(Building building, Employee employee) {
        building.setEmployee(employee);
        buildingDao.save(building);
    }

    public void updateBuilding(Building building) {
        buildingDao.update(building);
    }

    public void deleteBuilding(int id) {
        buildingDao.delete(id);
    }
    public void assignBuildingToEmployee(Building building) {
        Employee leastAssignedEmployee = employeeDao.findEmployeeWithLeastBuildings();

        if (leastAssignedEmployee != null) {
            building.setEmployee(leastAssignedEmployee);
            buildingDao.save(building);
        } else {
            throw new IllegalStateException("No available employees!");
        }
    }
    public List<Building> filterBuildingsByEmployee(int employeeId) {
        return buildingDao.findAll().stream()
                .filter(building -> building.getEmployee().getId() == employeeId)
                .collect(Collectors.toList());
    }


}
