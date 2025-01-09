import org.electronic_home_manager.dao.CompanyDao;
import org.electronic_home_manager.entity.Company;
import org.electronic_home_manager.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class CompanyServiceTest {

    private CompanyService companyService;

    @BeforeEach
    public void setUp() {
        companyService = new CompanyService(new CompanyDao());
    }

   /* @Test
    public void testAddCompany() {
        Company company = new Company("ABC Management", "123 Main St", "123456789", "abc@management.com");
        companyService.addCompany(company);
        List<Company> companies = companyService.getAllCompanies();
        assertTrue(companies.contains(company));
    }

    @Test
    public void testSortCompaniesByRevenue() {
        Company company1 = new Company("ABC Management", "123 Main St", "123456789", "abc@management.com");
        Company company2 = new Company("XYZ Services", "456 Side St", "987654321", "xyz@services.com");

        company1.setRevenue(5000);
        company2.setRevenue(7000);

        companyService.addCompany(company1);
        companyService.addCompany(company2);

        List<Company> sorted = companyService.sortCompaniesByRevenue();
        assertEquals("XYZ Services", sorted.get(0).getName());
    }*/
}

