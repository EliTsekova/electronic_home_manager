import org.electronic_home_manager.dao.ResidentDao;
import org.electronic_home_manager.entity.Resident;
import org.electronic_home_manager.service.ResidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ResidentServiceTest {

    private ResidentService residentService;

   /* @BeforeEach
    public void setUp() {
        residentService = new ResidentService(new ResidentDao());
    }*/

    @Test
    public void testAddResident() {
        Resident resident = new Resident("Ivan Ivanov", 35, true, null);
        residentService.addResident(resident);
        List<Resident> residents = residentService.getAllResidents();
        assertTrue(residents.contains(resident));
    }

    @Test
    public void testFilterResidentsByAge() {
        Resident resident1 = new Resident("Ivan Ivanov", 35, true, null);
        Resident resident2 = new Resident("Petar Petrov", 6, false, null);
        residentService.addResident(resident1);
        residentService.addResident(resident2);

        List<Resident> filtered = residentService.filterResidentsByAge(7);
        assertEquals(1, filtered.size());
        assertEquals("Ivan Ivanov", filtered.get(0).getName());
    }
}
