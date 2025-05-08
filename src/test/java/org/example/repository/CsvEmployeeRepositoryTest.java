package test.java.org.example.repository;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;
import main.java.org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
        Employee ceo = repository.getCEO();

        // Assert
        assertNotNull(ceo);
        assertEquals("123", ceo.getId());
        assertEquals("Joe", ceo.getFirstName());
        assertEquals("Doe", ceo.getLastName());
        assertEquals(60000, ceo.getSalary(), 0.01);
        assertNull(ceo.getManagerId());
        assertEquals(0, ceo.getDistanceFromCEO());
    }

    @Test
    public void testOrganizationalStructure() throws IOException {
        // Arrange
        String filePath = "src/test/java/org/example/resources/csvfile.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);

        // Act
        Map<String, Employee> organizationMap = repository.buildOrganizationalStructure();

        // Assert
        assertNotNull(organizationMap);
        assertEquals(5, organizationMap.size());

        // Check CEO
        Employee ceo = organizationMap.get("123");
        assertNotNull(ceo);
        assertEquals(2, ceo.getSubordinates().size());
        assertEquals(0, ceo.getDistanceFromCEO());

        // Check manager level
        Employee manager = organizationMap.get("124");
        assertNotNull(manager);
        assertEquals("123", manager.getManagerId());
        assertEquals(1, manager.getDistanceFromCEO());
        assertEquals(1, manager.getSubordinates().size());

        // Check employee level
        Employee employee = organizationMap.get("300");
        assertNotNull(employee);
        assertEquals("124", employee.getManagerId());
        assertEquals(2, employee.getDistanceFromCEO());
        assertEquals(1, employee.getSubordinates().size());
    }

    @Test
    public void testEmployeeDataWithoutHeader(@TempDir Path tempDir) throws IOException {
        // Arrange
        File csvFile = tempDir.resolve("test-data-without-header.csv").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("123,Joe,Doe,60000,\n");
            writer.write("124,Martin,Chekov,45000,123\n");
        }

        EmployeeRepository repository = new CsvEmployeeRepository(csvFile.getAbsolutePath());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            repository.getAllEmployees();
        });
    }

    @Test
    public void testEmployeeDataWithInvalidLine(@TempDir Path tempDir) throws IOException {
        // Arrange
        File csvFile = tempDir.resolve("test-data-invalid-line.csv").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("Id,firstName,lastName,salary,managerId\n");
            writer.write("123,Joe,Doe,60000,\n");
            writer.write("invalidLine\n"); // This should be skipped
            writer.write("124,Martin,Chekov,45000,123\n");
        }

        EmployeeRepository repository = new CsvEmployeeRepository(csvFile.getAbsolutePath());

        // Act
        List<Employee> employees = repository.getAllEmployees();

        // Assert
        assertNotNull(employees);
        assertEquals(2, employees.size());
    }

    @Test
    public void testInvalidFilePath() {
        // Arrange
        String filePath = "src/test/java/org/example/resources/invalidPath.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);

        // Assert
        assertThrows(IOException.class, () -> {
            repository.getAllEmployees();
        });
    }

    @Test
    public void testSubordinatesAverageSalary() throws IOException {
        // Arrange
        String filePath = "src/test/java/org/example/resources/csvfile.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);

        // Act
        Map<String, Employee> orgMap = repository.buildOrganizationalStructure();

        // Assert
        Employee manager = orgMap.get("124"); // Martin Chekov
        assertEquals(1, manager.getSubordinates().size());

        double avgSalary = manager.getSubordinatesAverageSalary();
        assertEquals(50000, avgSalary, 0.01); // Alice Hasacat's salary
    }
}