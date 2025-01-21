import org.electronic_home_manager.dao.*;
import org.electronic_home_manager.dto.CreateContractDto;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.entity.Contract;
import org.electronic_home_manager.entity.Employee;
import org.electronic_home_manager.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContractServiceTest {

    private ContractService contractService;
    private ContractDao contractDao;
    private CompanyDao companyDao;
    private BuildingDao buildingDao;
    private EmployeeDao employeeDao;

    @BeforeEach
    void setUp() {
        contractDao = mock(ContractDao.class);
        companyDao = mock(CompanyDao.class);
        buildingDao = mock(BuildingDao.class);
        employeeDao = mock(EmployeeDao.class);
        contractService = new ContractService(contractDao, companyDao, buildingDao, employeeDao);
    }

    @Test
    void testCreateContractWithMissingEntities() {
        // Mock behavior: company not found
        when(companyDao.findById(1L)).thenReturn(null);

        // Prepare contract DTO
        CreateContractDto contractDto = new CreateContractDto(1L, 2L, 3L, LocalDate.now(), LocalDate.now().plusMonths(6));

        // Assert exception is thrown with the correct message
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> contractService.createContract(contractDto));
        assertEquals("Company not found with ID: 1", exception.getMessage());
    }



    @Test
    void testCancelNonExistentContract() {
        // Mock DAO behavior: Contract not found
        when(contractDao.findById(999L)).thenReturn(null);

        // Assert exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> contractService.cancelContract(999L));
        assertEquals("Contract not found with ID: 999", exception.getMessage());
    }
}
