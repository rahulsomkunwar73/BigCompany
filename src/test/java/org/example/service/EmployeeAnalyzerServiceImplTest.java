package test.java.org.example.service;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;
import main.java.org.example.repository.EmployeeRepository;
import main.java.org.example.service.EmployeeAnalyzerService;
import main.java.org.example.service.EmployeeAnalyzerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
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

    @Test
    void calculateExcessReportingLevels(@TempDir Path tempDir) throws IOException {
        // Arrange
        File testDataFile = tempDir.resolve("long-reporting-lines.csv").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testDataFile))) {
            writer.write("Id,firstName,lastName,salary,managerId\n");
            writer.write("100,John,Smith,150000,\n");
            writer.write("101,Robert,Johnson,100000,100\n");
            writer.write("102,Michael,Williams,90000,100\n");
            writer.write("103,David,Brown,85000,101\n");
            writer.write("104,Richard,Jones,80000,102\n");
            writer.write("105,Thomas,Garcia,75000,103\n");
            writer.write("106,Charles,Miller,70000,104\n");
            writer.write("107,Daniel,Davis,65000,105\n");
            writer.write("108,Matthew,Rodriguez,60000,106\n");
            writer.write("109,Anthony,Wilson,55000,107\n");
            writer.write("110,Donald,Martinez,50000,108\n");
            writer.write("111,Steven,Anderson,45000,109\n");
            writer.write("112,Paul,Taylor,40000,110\n");
            writer.write("113,Andrew,Thomas,35000,111\n");
            writer.write("114,Joshua,Hernandez,30000,112\n");
            writer.write("115,Edward,Moore,28000,113\n");
        }
        EmployeeRepository repository = new CsvEmployeeRepository(testDataFile.getAbsolutePath());
        EmployeeAnalyzerService service = new EmployeeAnalyzerServiceImpl(repository);

        // Act
        List<Employee> employeesWithMaxReportingLine = service.findEmployeesWithLongReportingLines();

        // Assert
        assertNotNull(employeesWithMaxReportingLine);
        assertFalse(employeesWithMaxReportingLine.isEmpty());
        assertEquals(7, employeesWithMaxReportingLine.size(), "There should be 7 employees with long reporting lines");
    }


}
