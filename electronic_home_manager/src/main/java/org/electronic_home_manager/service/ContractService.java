package org.electronic_home_manager.service;

import jakarta.transaction.Transactional;
import org.electronic_home_manager.dao.BuildingDao;
import org.electronic_home_manager.dao.ContractDao;
import org.electronic_home_manager.dao.EmployeeDao;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.entity.Contract;
import org.electronic_home_manager.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public class ContractService {
    private final ContractDao contractDao;
    private final BuildingDao buildingDao;
    private final EmployeeDao employeeDao;

    public ContractService(ContractDao contractDao, BuildingDao buildingDao, EmployeeDao employeeDao) {
        this.contractDao = contractDao;
        this.buildingDao = buildingDao;
        this.employeeDao = employeeDao;
    }

    @Transactional
    public void createContract(Building building, LocalDate startDate, LocalDate endDate) {
        Employee leastAssignedEmployee = employeeDao.findEmployeeWithLeastBuildings();

        Contract contract = new Contract(building.getCompany(), building, leastAssignedEmployee, startDate, endDate);
        building.setEmployee(leastAssignedEmployee);
        buildingDao.save(building);
        contractDao.save(contract);
    }

        public List<Contract> getAllContracts () {
            return contractDao.findAll();
        }

        public Contract getContractById ( int id){
            return contractDao.findById(id);
        }

        public void createContract (Building building, Employee employee, Company company, LocalDate
        startDate, LocalDate endDate){
            Contract contract = new Contract(company, building, employee, startDate, endDate);
            contractDao.save(contract);
        }

        public void terminateContract ( int id){
            Contract contract = contractDao.findById(id);
            if (contract != null) {
                contract.setActive(false);
                contractDao.update(contract);
            }
        }
    }

