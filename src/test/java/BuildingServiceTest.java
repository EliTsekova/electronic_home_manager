import org.electronic_home_manager.dao.BuildingDao;
import org.electronic_home_manager.dao.EmployeeDao;
import org.electronic_home_manager.dto.CreateBuildingDto;
import org.electronic_home_manager.dto.EmployeeDto;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.entity.Employee;
import org.electronic_home_manager.service.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuildingServiceTest {

    private BuildingDao buildingDao;
    private EmployeeDao employeeDao;
    private BuildingService buildingService;

    @BeforeEach
    void setUp() {
        buildingDao = mock(BuildingDao.class);
        employeeDao = mock(EmployeeDao.class);
        buildingService = new BuildingService(buildingDao, employeeDao);
    }

    @Test
    void testCreateBuildingWithoutEmployee() {
        // Mock DAO behavior: No employee found
        when(employeeDao.findEmployeeWithLeastBuildings()).thenReturn(null);

        // Prepare building DTO
        CreateBuildingDto buildingDto = new CreateBuildingDto(null, "456 Elm St", 10, BigDecimal.valueOf(800), null);

        // Assert exception
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> buildingService.assignBuildingToEmployee(buildingDto));
        assertEquals("No available employees!", exception.getMessage());
    }

    @Test
    void testCreateBuildingWithEmployee() {
        // Mock EmployeeDto object
        EmployeeDto employeeDto = new EmployeeDto(1L, "Jane Doe", "jane.doe@example.com", "+1234567890");

        // Mock company object
        Company company = new Company();
        company.setId(1L);
        company.setName("Mock Company");

        // Create Employee entity
        Employee employee = new Employee("Jane Doe", "+1234567890", "jane.doe@example.com", company);
        employee.setId(1L);

        // Mock behavior for finding the least assigned employee
        when(employeeDao.findEmployeeWithLeastBuildings()).thenReturn(employeeDto);

        // Mock conversion from EmployeeDto to Employee
        when(employeeDao.toEntity(any(EmployeeDto.class))).thenReturn(employee);

        // Prepare CreateBuildingDto object
        CreateBuildingDto buildingDto = new CreateBuildingDto(null, "123 Park Ave", 15, BigDecimal.valueOf(1200), null);

        // Mock the behavior of updating a Building
        doAnswer(invocation -> {
            CreateBuildingDto updatedBuilding = invocation.getArgument(0);
            updatedBuilding.setId(1L); // Simulate that the building is updated with ID 1
            return null;
        }).when(buildingDao).update(any(CreateBuildingDto.class));

        // Call the method to assign the building to the employee
        buildingService.assignBuildingToEmployee(buildingDto);

        // Verify that the update method was called exactly once
        verify(buildingDao, times(1)).update(any(CreateBuildingDto.class));

        // Ensure the employee is assigned to the building correctly
        assertNotNull(buildingDto.getEmployee());
        assertEquals(employeeDto.getId(), buildingDto.getEmployee().getId());
    }





    @Test
    void testFilterBuildingsByEmployee() {
        // Mock data
        EmployeeDto employeeDto = new EmployeeDto(1L, "John Smith", "john.smith@example.com", "+9876543210");
        Company company = new Company("new", "address", "8888888888", "email@email.com");

        // Create Employee entity with a valid ID
        Employee employee = new Employee("John Smith", "8888888888", "john.smith@example.com", company);
        employee.setId(1L); // Set the ID to match the filter condition

        // Prepare building DTOs with employees assigned
        CreateBuildingDto building1 = new CreateBuildingDto(1L, "101 Main St", 8, BigDecimal.valueOf(500), null);
        CreateBuildingDto building2 = new CreateBuildingDto(2L, "202 Oak St", 10, BigDecimal.valueOf(700), null);
        building1.setEmployee(employee);
        building2.setEmployee(employee);

        // Mock DAO behavior
        when(buildingDao.findAll()).thenReturn(List.of(building1, building2));

        // Test filtering
        List<CreateBuildingDto> filteredBuildings = buildingService.filterBuildingsByEmployee(1L);

        // Assert results
        assertEquals(2, filteredBuildings.size());
        assertTrue(filteredBuildings.contains(building1));
        assertTrue(filteredBuildings.contains(building2));
    }

}
