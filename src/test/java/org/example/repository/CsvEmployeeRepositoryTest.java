package test.java.org.example.repository;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;
import main.java.org.example.repository.EmployeeRepository;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
}
