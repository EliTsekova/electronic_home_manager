package org.electronic_home_manager.service;

import org.electronic_home_manager.dao.CompanyDao;
import org.electronic_home_manager.dto.CreateCompanyDto;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.entity.Fee;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompanyService {
    private final CompanyDao companyDao;

    public CompanyService(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public List<CreateCompanyDto> getAllCompanies() {
        return companyDao.findAll();
    }

    public CreateCompanyDto getCompanyById(Long id) {
        return companyDao.findById(id);
    }

    public void createCompany(CreateCompanyDto companyDto) {
        if (companyDto.getName() == null || companyDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty.");
        }
        companyDao.save(companyDto);
    }

    public void updateCompany(CreateCompanyDto updatedCompanyDto) {
        CreateCompanyDto existingCompanyDto = companyDao.findById(updatedCompanyDto.getId());
        if (existingCompanyDto == null) {
            throw new IllegalArgumentException("Company not found with ID: " + updatedCompanyDto.getId());
        }
        companyDao.update(updatedCompanyDto);
    }

    public void deleteCompany(Long id) {
        CreateCompanyDto companyDto = companyDao.findById(id);
        if (companyDto == null) {
            throw new IllegalArgumentException("Company not found with ID: " + id);
        }
        companyDao.delete(id);
    }

    // Филтриране на компаниите по приходи
    public List<CreateCompanyDto> sortCompaniesByRevenue() {
        return companyDao.findAll().stream()
                .sorted(Comparator.comparing(this::calculateRevenue).reversed())
                .collect(Collectors.toList());
    }

    // Изчисляване на приходи за компания
    private BigDecimal calculateRevenue(CreateCompanyDto companyDto) {
        Company company = companyDao.toEntity(companyDto); // Конвертиране на DTO в Company
        if (company.getBuildings() == null) {
            return BigDecimal.ZERO;
        }

        return company.getBuildings().stream()
                .flatMap(building -> {
                    if (building.getApartments() == null) return Stream.<Fee>of();
                    return building.getApartments().stream()
                            .flatMap(apartment -> {
                                if (apartment.getFees() == null) return Stream.<Fee>of();
                                return apartment.getFees().stream();
                            });
                })
                .map(Fee::getAmount) // Assuming Fee::getAmount returns BigDecimal
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalRevenue() {
        return companyDao.findAll().stream()
                .map(this::calculateRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}