package org.electronic_home_manager.service;

import org.electronic_home_manager.entity.Fee;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileExportService {

    public void exportPaidFeesToFile(String filePath, List<Fee> fees) {
        StringBuilder fileContent = new StringBuilder("Paid Fees Report\n");
        fileContent.append("=================\n\n");

        if (fees.isEmpty()) {
            fileContent.append("No fees available to export.\n");
        } else {
            for (Fee fee : fees) {
                fileContent.append("Company: ").append(fee.getCompany().getName()).append("\n")
                        .append("Employee: ").append(fee.getEmployee() != null ? fee.getEmployee().getName() : "N/A").append("\n")
                        .append("Building: ").append(fee.getBuilding().getAddress()).append("\n")
                        .append("Apartment: ").append(fee.getApartment().getNumber()).append("\n")
                        .append("Amount: ").append(fee.getAmount()).append("\n")
                        .append("Payment Date: ").append(fee.getPaymentDate()).append("\n")
                        .append("-------------------------------\n");
            }
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(fileContent.toString());
            System.out.println("Data successfully exported to file: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to file: " + e.getMessage());
        }
    }
}
