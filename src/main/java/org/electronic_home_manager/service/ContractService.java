package org.electronic_home_manager.service;

import jakarta.transaction.Transactional;
import org.electronic_home_manager.dao.BuildingDao;
import org.electronic_home_manager.dao.CompanyDao;
import org.electronic_home_manager.dao.ContractDao;
import org.electronic_home_manager.dao.EmployeeDao;
import org.electronic_home_manager.dto.CreateBuildingDto;
import org.electronic_home_manager.dto.CreateCompanyDto;
import org.electronic_home_manager.dto.CreateContractDto;
import org.electronic_home_manager.dto.EmployeeDto;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.entity.Contract;
import org.electronic_home_manager.entity.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ContractService {
    private final ContractDao contractDao;
    private final CompanyDao companyDao;
    private final BuildingDao buildingDao;
    private final EmployeeDao employeeDao;

    public ContractService(ContractDao contractDao, CompanyDao companyDao, BuildingDao buildingDao, EmployeeDao employeeDao) {
        this.contractDao = contractDao;
        this.companyDao = companyDao;
        this.buildingDao = buildingDao;
        this.employeeDao = employeeDao;
    }

    @Transactional
    public void createContract(CreateContractDto contractDto) {
        // Validate the uniqueness of the contract
        validateUniqueContract(contractDto.getBuildingId());

        // Fetch the Company entity
        CreateCompanyDto companyDto = companyDao.findById(contractDto.getCompanyId());
        if (companyDto == null) {
            throw new IllegalArgumentException("Company not found with ID: " + contractDto.getCompanyId());
        }
        Company company = toEntity(companyDto);

        // Fetch the Building entity
        Building building = buildingDao.findById(contractDto.getBuildingId());
        if (building == null) {
            throw new IllegalArgumentException("Building not found with ID: " + contractDto.getBuildingId());
        }

        // Fetch the EmployeeDto and convert to Employee entity
        EmployeeDto employeeDto = employeeDao.findById(contractDto.getEmployeeId());
        if (employeeDto == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + contractDto.getEmployeeId());
        }
        Employee employee = toEntity(employeeDto);

        // Create the Contract entity
        Contract contract = new Contract(company, building, employee, contractDto.getStartDate(), contractDto.getEndDate());
        contractDao.save(contract);

        // Update the Building entity with the assigned Employee
        building.setEmployee(employee);
        buildingDao.update(toDto(building));
    }




    // Validate that no active contract exists for the building
    private void validateUniqueContract(Long buildingId) {
        boolean exists = contractDao.findAll().stream()
                .anyMatch(contract -> contract.getBuilding().getId().equals(buildingId) && contract.isActive());
        if (exists) {
            throw new IllegalArgumentException("Active contract already exists for building ID: " + buildingId);
        }
    }

    // Retrieve all contracts
    public List<CreateContractDto> getAllContracts() {
        return contractDao.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Retrieve active contracts
    public List<CreateContractDto> getActiveContracts() {
        return contractDao.findAll().stream()
                .filter(Contract::isActive)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Cancel a contract
    @Transactional
    public void cancelContract(Long contractId) {
        // Fetch the contract by ID
        Contract contract = contractDao.findById(contractId);
        if (contract == null) {
            throw new IllegalArgumentException("Contract not found with ID: " + contractId);
        }

        // Mark the contract as inactive
        contract.setActive(false);
        contractDao.update(contract);

        // Fetch the associated building and set its employee to null
        Building building = contract.getBuilding();
        if (building != null) {
            building.setEmployee(null);
            buildingDao.update(toDto(building));
        }
    }


    /// Convert Building entity to CreateBuildingDto
    private CreateBuildingDto toDto(Building building) {
        if (building == null) {
            return null;
        }
        return new CreateBuildingDto(
                building.getId(),
                building.getAddress(),
                building.getFloors(),
                building.getCommonArea(),
                building.getCompany() != null ? building.getCompany().getId() : null
        );
    }



    // Update a contract
    @Transactional
    public void updateContract(Long contractId, LocalDate newStartDate, LocalDate newEndDate) {
        Contract contract = contractDao.findById(contractId);
        if (contract == null) {
            throw new IllegalArgumentException("Contract not found with ID: " + contractId);
        }
        contract.setStartDate(newStartDate);
        contract.setEndDate(newEndDate);
        contractDao.update(contract);
    }

    // Helper: Convert Contract to CreateContractDto
    private CreateContractDto toDto(Contract contract) {
        return new CreateContractDto(
                contract.getCompany().getId(),
                contract.getBuilding().getId(),
                contract.getEmployee().getId(),
                contract.getStartDate(),
                contract.getEndDate()
        );
    }
    private Company toEntity(CreateCompanyDto companyDto) {
        Company company = new Company();
        company.setName(companyDto.getName());
        company.setAddress(""); // Populate address if available
        company.setPhone("");   // Populate phone if available
        company.setEmail("");   // Populate email if available
        return company;
    }
    private Employee toEntity(EmployeeDto employeeDto) {
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