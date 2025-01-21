import org.electronic_home_manager.dao.*;
import org.electronic_home_manager.dto.CreateContractDto;
import org.electronic_home_manager.dto.CreateFeeDto;
import org.electronic_home_manager.entity.*;
import org.electronic_home_manager.service.BuildingService;
import org.electronic_home_manager.service.ContractService;
import org.electronic_home_manager.service.FeeService;
import org.electronic_home_manager.service.FileExportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileExportServiceTest {

    private ApartmentDao apartmentDao;
    private FeeDao feeDao;
    private FileExportService fileExportService;
    private FeeService feeService;
    private CompanyDao companyDao;
    private BuildingDao buildingDao;
    private ContractService contractService;
    private Employee employeeDao;


    @BeforeEach
    void setup() {
        // Initialize mocks
        feeDao = Mockito.mock(FeeDao.class);
        apartmentDao = Mockito.mock(ApartmentDao.class);
        fileExportService = Mockito.mock(FileExportService.class);

        // Create FeeService with mocked dependencies
        feeService = new FeeService(feeDao, apartmentDao, fileExportService);
    }

    @Test
    void testExportPaidFees() {
        // Step 1: Mock fees
        Fee fee1 = new Fee(null, null, BigDecimal.valueOf(100), LocalDate.now(), null);
        Fee fee2 = new Fee(null, null, BigDecimal.valueOf(150), LocalDate.now(), null);
        Mockito.when(feeDao.findAll()).thenReturn(List.of(fee1, fee2));

        // Step 2: Export fees
        feeService.exportPaidFees("output.txt");

        // Verify export call
        Mockito.verify(fileExportService, Mockito.times(1)).exportPaidFeesToFile(Mockito.eq("output.txt"), Mockito.anyList());
    }
    @Test
    void testCreateFeesAndExportToFile() {
        // Mock data
        Building building = new Building("300 Hilltop Rd", 10, BigDecimal.valueOf(1200), null);
        building.setId(1L);
        Apartment apartment = new Apartment(101, BigDecimal.valueOf(150), true, building);
        apartment.setId(101L);

        Fee fee1 = new Fee(apartment, building, BigDecimal.valueOf(100), LocalDate.now(), null);
        Fee fee2 = new Fee(apartment, building, BigDecimal.valueOf(200), LocalDate.now(), null);

        Mockito.when(feeDao.findAll()).thenReturn(List.of(fee1, fee2));

        // Mock FileExportService
        FileExportService fileExportService = Mockito.mock(FileExportService.class);
        FeeService feeServiceWithMockExport = new FeeService(feeDao, apartmentDao, fileExportService);

        // Export fees
        feeServiceWithMockExport.exportPaidFees("fees_output.txt");

        // Verify that fees were exported correctly
        Mockito.verify(fileExportService, Mockito.times(1))
                .exportPaidFeesToFile(Mockito.eq("fees_output.txt"), Mockito.argThat(fees ->
                        fees.contains(fee1) && fees.contains(fee2)
                ));
    }
    @Test
    void testCreateContractWithInvalidIds() {
        // Mock DAOs
        ContractDao contractDao = Mockito.mock(ContractDao.class);
        CompanyDao companyDao = Mockito.mock(CompanyDao.class);
        BuildingDao buildingDao = Mockito.mock(BuildingDao.class);
        EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
        ContractService contractService = new ContractService(contractDao, companyDao, buildingDao, employeeDao);

        // Mock invalid IDs
        Mockito.when(companyDao.findById(999L)).thenReturn(null); // Company not found
        Mockito.when(buildingDao.findById(999L)).thenReturn(null); // Building not found
        Mockito.when(employeeDao.findById(999L)).thenReturn(null); // Employee not found

        // Attempt to create a contract
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contractService.createContract(new CreateContractDto(999L, 999L, 999L, LocalDate.now(), null))
        );

        // Assert the exception message
        assertEquals("Company not found with ID: 999", exception.getMessage());
    }


    @Test
    void testAddFeeForNonExistentApartment() {
        // Mock non-existent apartment
        Mockito.when(apartmentDao.findById(999L)).thenReturn(null);

        // Attempt to create a fee
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                feeService.createFee(new CreateFeeDto(999L, BigDecimal.ZERO, LocalDate.now()))
        );

        assertEquals("Apartment not found with ID: 999", exception.getMessage());
    }





}
