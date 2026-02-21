package com.java8.demo.loader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.java8.demo.EmoloyeeOperations;
import com.java8.demo.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.java8.demo.model.Employee;

@Component
public class EmployeeDataInitializer implements CommandLineRunner {

    private final EmployeeService employeeService;

    public EmployeeDataInitializer(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (employeeService.count() > 0) {
            System.out.println("Employees already initialized: " + employeeService.count());
            return;
        }

        List<Employee> employees = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new Employee(
                        sampleFirstName(i),
                        sampleLastName(i),
                        sampleEmail(i),
                        22 + (i % 25),
                        sampleDepartment(i),
                        samplePosition(i),
                        randomSalary(i),
                        LocalDate.now().minusYears(i % 10).minusMonths(i % 12),
                        i % 3 != 0 // make some part-time
                ))
                .collect(Collectors.toList());

        // Save each employee via the service by calling save for each item
        employees.forEach(employeeService::save);
        EmoloyeeOperations employeeOperations = new EmoloyeeOperations(employeeService);
        employeeOperations.operations();

        System.out.println("Initialized " + employeeService.count() + " employees into H2 database.");
    }

    private static BigDecimal randomSalary(int i) {
        double min = 30000.0;
        double max = 120000.0;
        double val = ThreadLocalRandom.current().nextDouble(min, max);
        return BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
    }

    private static String sampleFirstName(int i) {
        String[] names = {"John", "Jane", "Alex", "Emily", "Michael", "Sarah", "David", "Laura", "Robert", "Olivia"};
        return names[(i - 1) % names.length];
    }

    private static String sampleLastName(int i) {
        String[] lasts = {"Smith", "Johnson", "Brown", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin"};
        return lasts[(i - 1) % lasts.length] + i;
    }

    private static String sampleEmail(int i) {
        return "employee" + i + "@example.com";
    }

    private static String sampleDepartment(int i) {
        String[] depts = {"Engineering", "HR", "Sales", "Marketing", "Finance"};
        return depts[(i - 1) % depts.length];
    }

    private static String samplePosition(int i) {
        String[] positions = {"Engineer", "Manager", "Analyst", "Coordinator", "Specialist"};
        return positions[(i - 1) % positions.length];
    }
}
