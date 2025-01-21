import org.electronic_home_manager.dao.ApartmentDao;
import org.electronic_home_manager.dao.BuildingDao;
import org.electronic_home_manager.dao.FeeDao;
import org.electronic_home_manager.dto.CreateFeeDto;
import org.electronic_home_manager.entity.*;
import org.electronic_home_manager.service.FeeService;
import org.electronic_home_manager.service.FileExportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class FeeServiceTest {

    private ApartmentDao apartmentDao;
    private FeeDao feeDao;
    private FileExportService fileExportService;
    private FeeService feeService;

    @BeforeEach
    void setup() {
        apartmentDao = Mockito.mock(ApartmentDao.class);
        feeDao = Mockito.mock(FeeDao.class);
        fileExportService = Mockito.mock(FileExportService.class);
        feeService = new FeeService(feeDao, apartmentDao, fileExportService);
    }

    @Test
    void testCreateFeeForNonExistentApartment() {
        Mockito.when(apartmentDao.findById(999L)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> feeService.createFee(new CreateFeeDto(999L, BigDecimal.ZERO, LocalDate.now())));
        assertEquals("Apartment not found with ID: 999", exception.getMessage());
    }

    @Test
    void testCreateFeeWithVariousConditions() {
        // Създаване на сграда и апартамент
        Company company = new Company("LLL", "add", "8888888880", "email@email.email");
        Building building = new Building("Test St", 10, BigDecimal.valueOf(500), company);
        building.setId(1L);

        Apartment apartment = new Apartment(101, BigDecimal.valueOf(100), true, building);
        apartment.setId(101L);

        // Жители на апартамента
        List<Resident> residents = List.of(
                new Resident("John", 30, true, apartment), // Възрастен с асансьор
                new Resident("Jane", 5, true, apartment)   // Дете с асансьор
        );

        apartment.setResidents(residents);

        // Мокване на `apartmentDao` да върне апартамента
        Mockito.when(apartmentDao.findById(101L)).thenReturn(apartment);

        // Мокване на метода fetchResidents
        FeeService feeServiceSpy = Mockito.spy(feeService);
        Mockito.doReturn(residents).when(feeServiceSpy).fetchResidents(apartment);

        // Извикване на метода за създаване на такса
        Fee fee = feeServiceSpy.createFee(new CreateFeeDto(101L, BigDecimal.ZERO, LocalDate.now()));

        // Проверки
        assertNotNull(fee, "Fee should not be null.");
        assertEquals(BigDecimal.valueOf(5.0), fee.getAmount(), "Fee amount should match the calculated value.");
        assertEquals(apartment, fee.getApartment(), "Fee should be associated with the correct apartment.");
        assertEquals(building, fee.getBuilding(), "Fee should be associated with the correct building.");
    }








    @Test
    void testExportPaidFeesWhenNoFeesAvailable() {
        Mockito.when(feeDao.findAll()).thenReturn(List.of());

        feeService.exportPaidFees("output.txt");

        Mockito.verify(fileExportService, Mockito.never()).exportPaidFeesToFile(anyString(), any());
    }

    @Test
    void testGenerateFeeSummaryByCompany() {
        Company companyA = new Company("Company A", "123 Main St", "+123456789", "a@example.com");
        Company companyB = new Company("Company B", "456 Main St", "+987654321", "b@example.com");

        Fee fee1 = new Fee(null, null, BigDecimal.valueOf(100), LocalDate.now(), null);
        fee1.setCompany(companyA);
        Fee fee2 = new Fee(null, null, BigDecimal.valueOf(200), LocalDate.now(), null);
        fee2.setCompany(companyA);
        Fee fee3 = new Fee(null, null, BigDecimal.valueOf(300), LocalDate.now(), null);
        fee3.setCompany(companyB);

        Mockito.when(feeDao.findAll()).thenReturn(List.of(fee1, fee2, fee3));

        Map<String, BigDecimal> summary = feeService.generateFeeSummaryByCompany();

        assertEquals(BigDecimal.valueOf(300), summary.get("Company A"));
        assertEquals(BigDecimal.valueOf(300), summary.get("Company B"));
    }

    @Test
    void testGenerateFeeSummaryByBuilding() {
        Building building1 = new Building("Building A", 5, BigDecimal.valueOf(500), null);
        Building building2 = new Building("Building B", 10, BigDecimal.valueOf(1000), null);

        Fee fee1 = new Fee(null, building1, BigDecimal.valueOf(150), LocalDate.now(), null);
        Fee fee2 = new Fee(null, building1, BigDecimal.valueOf(250), LocalDate.now(), null);
        Fee fee3 = new Fee(null, building2, BigDecimal.valueOf(200), LocalDate.now(), null);

        Mockito.when(feeDao.findAll()).thenReturn(List.of(fee1, fee2, fee3));

        Map<String, BigDecimal> summary = feeService.generateFeeSummaryByBuilding();

        assertEquals(BigDecimal.valueOf(400), summary.get("Building A"));
        assertEquals(BigDecimal.valueOf(200), summary.get("Building B"));
    }
}
