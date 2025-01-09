package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.EmployeeDao;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeDao.findById(id);
    }

    public void addEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeDao.update(employee);
    }

    public void deleteEmployee(int id) {
        employeeDao.delete(id);
    }


    public void reassignBuildings(Employee leavingEmployee) {
        List<Building> buildings = leavingEmployee.getBuildings();
        List<Employee> remainingEmployees = employeeDao.findAll();

        if (!remainingEmployees.isEmpty()) {
            for (Building building : buildings) {
                Employee leastLoadedEmployee = remainingEmployees
                        .stream()
                        .min((e1, e2) -> Integer.compare(e1.getBuildings().size(), e2.getBuildings().size()))
                        .orElse(null);
                building.setEmployee(leastLoadedEmployee);
            }
        }
    }
    public List<Employee> sortEmployeesByBuildingCount() {
        return employeeDao.findAll().stream()
                .sorted(Comparator.comparing(employee -> employee.getBuildings().size(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

}

