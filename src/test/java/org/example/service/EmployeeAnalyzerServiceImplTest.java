package test.java.org.example.service;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;
import main.java.org.example.repository.EmployeeRepository;
import main.java.org.example.service.EmployeeAnalyzerService;
import main.java.org.example.service.EmployeeAnalyzerServiceImpl;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeAnalyzerServiceImplTest {

    @Test
    public void findManagersEarningLessThanRequired() throws IOException {
        // Arrange
        String filePath = "src/test/java/org/example/resources/csvfile.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);
        EmployeeAnalyzerService service = new EmployeeAnalyzerServiceImpl(repository);

        // Act
        List<Employee> underpaidManagers = service.findManagersEarningLessThanRequired();

        // Assert
        assertEquals(1, underpaidManagers.size());
        assertEquals("124", underpaidManagers.get(0).getId());
    }

    @Test
    public void findManagersEarningMoreThanAllowed() throws IOException {

        String filePath = "src/test/java/org/example/resources/csvfile.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);
        EmployeeAnalyzerService service = new EmployeeAnalyzerServiceImpl(repository);

        // Act
        List<Employee> overpaidManagers = service.findManagersEarningMoreThanAllowed();

        // Assert
        assertNotNull(overpaidManagers);
        assertEquals(1, overpaidManagers.size());
        assertEquals("300", overpaidManagers.get(0).getId()); // Alice Hasacat should be overpaid
    }


}
