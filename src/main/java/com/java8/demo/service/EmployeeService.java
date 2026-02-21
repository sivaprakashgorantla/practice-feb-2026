package com.java8.demo.service;

import java.util.List;
import java.util.Optional;

import com.java8.demo.model.Employee;

public interface EmployeeService {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee employee);
    Employee update(Long id, Employee employee);
    void deleteById(Long id);
    long count();
    List<Employee> findByDepartment(String department);
    List<Employee> findByMinAge(int minAge);
    Optional<Employee> findByEmail(String email);
}

