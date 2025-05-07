package test.java.org.example.repository;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;
import main.java.org.example.repository.EmployeeRepository;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CsvEmployeeRepositoryTest {

    @Test
    public void testGetAllEmployees() throws IOException {
        // Arrange
        String filePath = "src/test/java/org/example/resources/csvfile.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);

        // Act
        List<Employee> employees = repository.getAllEmployees();

        // Assert
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testCEOData() throws IOException {
        // Arrange
        String filePath = "src/test/java/org/example/resources/csvfile.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);

        // Act
        List<Employee> employees = repository.getAllEmployees();

        // Assert

        Employee ceo = employees.stream()
                .filter(e -> e.getId().equals("123"))
                .findFirst()
                .orElse(null);

        assertNotNull(ceo);
        assertEquals("Joe", ceo.getFirstName());
        assertEquals("Doe", ceo.getLastName());
        assertEquals(60000, ceo.getSalary());
        assertNull(ceo.getManagerId());

    }


}
