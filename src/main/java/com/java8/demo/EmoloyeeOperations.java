package com.java8.demo;

import com.java8.demo.model.Employee;
import com.java8.demo.model.Student;
import com.java8.demo.service.EmployeeService;
import com.java8.demo.service.StudentService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EmoloyeeOperations {

    private final EmployeeService employeeService;

    public EmoloyeeOperations(EmployeeService employeeService ) {
        this.employeeService = employeeService;
    }

    public void operations() {
        System.out.println("Performing student operations...");


        List<Employee> employees = employeeService.findAll();
        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees available to evaluate.");
            return;
        }
        employeeSortByValueEmployeeSalary(employees);
        employeeNthHighestSalary(employees, 3);
        employeeDuplicateEmployee(employees);
    }

    private void employeeDuplicateEmployee(List<Employee> employees) {
        System.out.println("First Duplicate Employee based on email:");

        Optional<Employee> firstDuplicate = employees.stream()
                .collect(Collectors.groupingBy(Employee::getEmail))
                .values()
                .stream()
                .filter(list -> list.size() > 1)
                .flatMap(List::stream)
                .findFirst();
        firstDuplicate.ifPresent(emp ->
                System.out.println("First duplicate employee: " + emp));

        System.out.println("Duplicate Employees based on email:");
        Map<String, List<Employee>> duplicateEmployees = employees.stream()
                .collect(Collectors.groupingBy(Employee::getEmail))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (duplicateEmployees.isEmpty()) {
            System.out.println("No duplicate employees found based on email.");
        } else {
            duplicateEmployees.forEach((email, empList) -> {
                System.out.println("Email: " + email);
                empList.forEach(emp -> System.out.println(" - " + emp));
            });
        }

    }

    private void employeeNthHighestSalary(List<Employee> employees, int i) {
        System.out.println("Employee with " + i + "rd highest salary:");
        employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .skip(i - 1)
                .findFirst()
                .ifPresent(employee -> System.out.println("Employee with " + i + "rd highest salary: " + employee));
    }

    private void employeeSortByValueEmployeeSalary(List<Employee> employees) {
        System.out.println(" List of employees Sorting by Salary");
        employees.stream()
                .collect(Collectors.toMap(
                        Employee::getId,
                        Function.identity(),
                        (e1,e2) -> e1,
                        LinkedHashMap::new
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Employee>comparingByValue(Comparator.comparing(Employee::getSalary)))
                .forEach(entry ->
                        System.out.println("Employee sorted by Salary: " + entry.getValue()));
    }
}
