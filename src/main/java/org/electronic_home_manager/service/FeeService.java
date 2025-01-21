package org.electronic_home_manager.service;

import jakarta.transaction.Transactional;
import org.electronic_home_manager.dao.ApartmentDao;
import org.electronic_home_manager.dao.FeeDao;
import org.electronic_home_manager.dto.CreateFeeDto;
import org.electronic_home_manager.entity.*;
import org.electronic_home_manager.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeeService {
    private final FeeDao feeDao;
    private final ApartmentDao apartmentDao;
    private final FileExportService fileExportService;

    public FeeService(FeeDao feeDao, ApartmentDao apartmentDao, FileExportService fileExportService) {
        this.feeDao = feeDao;
        this.apartmentDao = apartmentDao;
        this.fileExportService = fileExportService;
    }

    @Transactional
    public Fee createFee(CreateFeeDto feeDto) {
        Apartment apartment = apartmentDao.findById(feeDto.getApartmentId());
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment not found with ID: " + feeDto.getApartmentId());
        }

        BigDecimal calculatedAmount = calculateTotalFee(apartment);
        Fee fee = new Fee(apartment, apartment.getBuilding(), calculatedAmount, feeDto.getPaymentDate(), apartment.getBuilding().getEmployee());
        fee.setCompany(apartment.getBuilding().getCompany());
        feeDao.save(fee);

        return fee;
    }

    public BigDecimal calculateTotalFee(Apartment apartment) {
        List<Resident> residents = fetchResidents(apartment);
        return residents.stream()
                .map(resident -> BigDecimal.valueOf(resident.getAge() > 7 && resident.isUsesElevator() ? 5.0 : 0))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Resident> fetchResidents(Apartment apartment) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Apartment persistentApartment = session.get(Apartment.class, apartment.getId());
            if (persistentApartment == null) {
                throw new IllegalArgumentException("Apartment not found in the database with ID: " + apartment.getId());
            }
            Hibernate.initialize(persistentApartment.getResidents());
            return persistentApartment.getResidents();
        }
    }

    public void exportPaidFees(String filePath) {
        List<Fee> fees = feeDao.findAll();
        if (fees.isEmpty()) {
            System.out.println("No fees found for export.");
        } else {
            fileExportService.exportPaidFeesToFile(filePath, fees);
        }
    }

    public Map<String, BigDecimal> generateFeeSummaryByCompany() {
        return feeDao.findAll().stream()
                .collect(Collectors.groupingBy(
                        fee -> fee.getCompany().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Fee::getAmount, BigDecimal::add)
                ));
    }

    public Map<String, BigDecimal> generateFeeSummaryByBuilding() {
        return feeDao.findAll().stream()
                .collect(Collectors.groupingBy(
                        fee -> fee.getBuilding().getAddress(),
                        Collectors.reducing(BigDecimal.ZERO, Fee::getAmount, BigDecimal::add)
                ));
    }

    public BigDecimal calculateTotalFeesByBuilding(Long buildingId) {
        return feeDao.findByBuilding(buildingId).stream()
                .map(Fee::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculatePaidFeesByCompany(Long companyId) {
        return feeDao.findByCompanyId(companyId).stream()
                .map(Fee::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public Map<String, BigDecimal> calculateFeesByCompany() {
        return feeDao.findAll().stream()
                .collect(Collectors.groupingBy(
                        fee -> fee.getCompany().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Fee::getAmount, BigDecimal::add)
                ));
    }

    public Map<String, BigDecimal> calculateFeesByBuilding() {
        return feeDao.findAll().stream()
                .collect(Collectors.groupingBy(
                        fee -> fee.getBuilding().getAddress(),
                        Collectors.reducing(BigDecimal.ZERO, Fee::getAmount, BigDecimal::add)
                ));
    }

    public Map<String, BigDecimal> calculateFeesByEmployee() {
        return feeDao.findAll().stream()
                .filter(fee -> fee.getEmployee() != null)
                .collect(Collectors.groupingBy(
                        fee -> fee.getEmployee().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Fee::getAmount, BigDecimal::add)
                ));
    }
    private CreateFeeDto toDto(Fee fee) {
        return new CreateFeeDto(
                fee.getApartment().getId(),
                fee.getAmount(),
                fee.getPaymentDate()
        );
    }

}
