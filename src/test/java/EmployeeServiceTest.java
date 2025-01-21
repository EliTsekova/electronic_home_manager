import org.electronic_home_manager.dao.*;
import org.electronic_home_manager.dto.EmployeeDto;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.entity.Employee;
import org.electronic_home_manager.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import org.electronic_home_manager.dao.*;
import org.electronic_home_manager.dto.CreateBuildingDto;
import org.electronic_home_manager.dto.EmployeeDto;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.entity.Employee;
import org.electronic_home_manager.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeDao employeeDao;
    private BuildingDao buildingDao;

    @BeforeEach
    void setUp() {
        employeeDao = Mockito.mock(EmployeeDao.class);
        buildingDao = Mockito.mock(BuildingDao.class);
        employeeService = new EmployeeService(employeeDao, buildingDao);
    }

    @Test
    void testReassignBuildingsFromLeavingEmployee() {
        // Mock the company
        Company company = new Company();
        company.setId(1L);
        company.setName("Test Company");

        // Mock leaving employee DTO and entity
        EmployeeDto leavingEmployeeDto = new EmployeeDto(1L, "John Manager", "john.manager@example.com", "+123456789");
        Employee leavingEmployee = new Employee("John Manager", "+123456789", "john.manager@example.com", company);
        leavingEmployee.setId(1L);

        // Mock target employees
        EmployeeDto targetEmployeeDto1 = new EmployeeDto(2L, "Jane Supervisor", "jane.supervisor@example.com", "+987654321");
        Employee targetEmployee1 = new Employee("Jane Supervisor", "+987654321", "jane.supervisor@example.com", company);
        targetEmployee1.setId(2L);
        targetEmployee1.setBuildings(new ArrayList<>());

        EmployeeDto targetEmployeeDto2 = new EmployeeDto(3L, "Mark Coordinator", "mark.coordinator@example.com", "+1122334455");
        Employee targetEmployee2 = new Employee("Mark Coordinator", "+1122334455", "mark.coordinator@example.com", company);
        targetEmployee2.setId(3L);
        targetEmployee2.setBuildings(new ArrayList<>());

        // Mock buildings
        Building building1 = new Building("Building 1", 10, BigDecimal.valueOf(1000), company);
        building1.setId(101L);
        building1.setEmployee(leavingEmployee);

        Building building2 = new Building("Building 2", 20, BigDecimal.valueOf(2000), company);
        building2.setId(102L);
        building2.setEmployee(leavingEmployee);

        // Use a mutable list for buildings
        leavingEmployee.setBuildings(new ArrayList<>(List.of(building1, building2)));

        // Mock conversions to DTO
        CreateBuildingDto buildingDto1 = new CreateBuildingDto(101L, "Building 1", 10, BigDecimal.valueOf(1000), targetEmployee1.getId());
        CreateBuildingDto buildingDto2 = new CreateBuildingDto(102L, "Building 2", 20, BigDecimal.valueOf(2000), targetEmployee2.getId());

        // Mock DAO behavior
        Mockito.when(employeeDao.toEntity(leavingEmployeeDto)).thenReturn(leavingEmployee);
        Mockito.when(employeeDao.findAll()).thenReturn(List.of(targetEmployeeDto1, targetEmployeeDto2));
        Mockito.when(employeeDao.toEntity(targetEmployeeDto1)).thenReturn(targetEmployee1);
        Mockito.when(employeeDao.toEntity(targetEmployeeDto2)).thenReturn(targetEmployee2);
        Mockito.when(buildingDao.toDto(building1)).thenReturn(buildingDto1);
        Mockito.when(buildingDao.toDto(building2)).thenReturn(buildingDto2);
        Mockito.doNothing().when(buildingDao).update(any(CreateBuildingDto.class));

        // Execute the reassignBuildings method
        employeeService.reassignBuildings(leavingEmployeeDto);

        // Verify that the buildings are reassigned
        Mockito.verify(buildingDao, Mockito.times(2)).update(any(CreateBuildingDto.class));
        assertTrue(leavingEmployee.getBuildings().isEmpty(), "Leaving employee should have no buildings.");
        assertEquals(1, targetEmployee1.getBuildings().size(), "Target employee 1 should have one building.");
        assertEquals(1, targetEmployee2.getBuildings().size(), "Target employee 2 should have one building.");
        assertEquals(building1, targetEmployee1.getBuildings().get(0), "Building 1 should be assigned to target employee 1.");
        assertEquals(building2, targetEmployee2.getBuildings().get(0), "Building 2 should be assigned to target employee 2.");
    }

}