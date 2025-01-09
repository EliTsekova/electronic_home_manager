import org.electronic_home_manager.dao.BuildingDao;
import org.electronic_home_manager.entity.Building;
import org.electronic_home_manager.entity.Employee;
import org.electronic_home_manager.service.BuildingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class BuildingServiceTest {

   /* private BuildingService buildingService;

    @BeforeEach
    public void setUp() {
        buildingService = new BuildingService(new BuildingDao());
    }
/*
    @Test
    public void testAddBuilding() {
        Building building = new Building("456 High St", 8, 200, null);
        buildingService.addBuilding(building);
        List<Building> buildings = buildingService.getAllBuildings();
        assertTrue(buildings.contains(building));
    }

    @Test
    public void testFilterBuildingsByEmployee() {
        Employee employee = new Employee("John Doe", "janitor");
        Building building1 = new Building("789 Oak St", 10, 250, null);
        Building building2 = new Building("321 Elm St", 5, 150, null);

        building1.setEmployee(employee);
        building2.setEmployee(employee);
        buildingService.addBuilding(building1);
        buildingService.addBuilding(building2);

        List<Building> filtered = buildingService.filterBuildingsByEmployee(employee.getId());
        assertEquals(2, filtered.size());
    }
}*/
