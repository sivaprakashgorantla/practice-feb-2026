package com.java8.demo;

import com.java8.demo.model.Employee;
import com.java8.demo.model.Student;
import com.java8.demo.service.EmployeeService;
import com.java8.demo.service.StudentService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
