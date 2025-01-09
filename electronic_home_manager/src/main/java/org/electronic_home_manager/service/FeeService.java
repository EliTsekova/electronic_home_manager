package org.electronic_home_manager.service;

import jakarta.transaction.Transactional;
import org.electronic_home_manager.dao.FeeDao;
import org.electronic_home_manager.dao.ResidentDao;
import org.electronic_home_manager.entity.Apartment;
import org.electronic_home_manager.entity.Fee;
import org.electronic_home_manager.entity.Resident;
import org.electronic_home_manager.entity.Company;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FeeService {
    private final FeeDao feeDao;
    private final ResidentDao residentDao;

    public FeeService(FeeDao feeDao, ResidentDao residentDao) {
        this.feeDao = feeDao;
        this.residentDao = residentDao;
    }

   /* @Transactional
    public Fee calculateAndSaveFee(Apartment apartment) {
        double baseFee = apartment.getArea() * 0.5;
        double residentFee = calculateResidentFee(apartment.getId());
        double petFee = apartment.isHasPet() ? 20.0 : 0.0;

        double totalFee = baseFee + residentFee + petFee;

        Fee fee = new Fee(apartment, apartment.getBuilding(), totalFee, LocalDate.now(), apartment.getBuilding().getEmployee());
        feeDao.save(fee);
        return fee;
    }*/


    private double calculateResidentFee(int apartmentId) {
        List<Resident> residents = residentDao.findByApartmentId(apartmentId);
        return residents.stream()
                .filter(resident -> resident.getAge() > 7 && resident.isUsesElevator())
                .mapToDouble(resident -> 5.0)
                .sum();
    }


   /* public void generateFeeReport() {
        List<Fee> fees = feeDao.findAll();
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("");

        for (Fee fee : fees) {
            reportBuilder.append(fee.getCompany().getName()).append(", ")
                    .append(fee.getEmployee().getName()).append(", ")
                    .append(fee.getBuilding().getAddress()).append(", ")
                    .append(fee.getApartment().getNumber()).append(", ")
                    .append(fee.getAmount()).append(", ")
                    .append(fee.getPaymentDate()).append("\n");
        }

        saveReportToFile(reportBuilder.toString());
    }
*/
    private void saveReportToFile(String reportContent) {
        try {
            String filePath = "fee_report_" + LocalDate.now() + ".csv";
            java.nio.file.Files.write(java.nio.file.Paths.get(filePath), reportContent.getBytes());
            System.out.println("The ... is written in: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public List<Fee> getAllFees() {
        return feeDao.findAll();
    }

    public Fee getFeeById(int id) {
        return feeDao.findById(id);
    }

    public Fee createFee(Apartment apartment, LocalDate date) {
        double calculatedAmount = calculateFee(apartment);
        Fee fee = new Fee(apartment, apartment.getBuilding(), calculatedAmount, date, apartment.getBuilding().getEmployee());
        feeDao.save(fee);
        return fee;
    }

    public double calculateFee(Resident resident) {
        double baseFee = 20.0; // Example base fee
        double elevatorFee = resident.calculateElevatorFee();
        return baseFee + elevatorFee;
    }

    //or
    public double calculateFee(Apartment apartment) {
        double baseFee = apartment.getArea() * 0.5; // 0.5 за кв. м.

        for (Resident resident : apartment.getResidents()) {
            if (resident.getAge() > 7 && resident.isUsesElevator()) {
                baseFee += 5.0;
            }
        }

        if (apartment.isHasPet()) {
            baseFee += 10.0;
        }

        return baseFee;
    }

    public void generateFeeReport(String filePath) {
        FeeDao feeDao = null;
        List<Fee> fees = feeDao.findAll();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ID, Apartment, Building, Amount, Payment Date, Employee\n");

            for (Fee fee : fees) {
                writer.write(String.format("%d, %s, %s, %.2f, %s, %s\n",
                        fee.getId(),
                        fee.getApartment().getId(),
                        fee.getBuilding().getAddress(),
                        fee.getAmount(),
                        fee.getPaymentDate().toString(),
                        fee.getEmployee().getName()
                ));
            }

            System.out.println("The ... is written in:" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

