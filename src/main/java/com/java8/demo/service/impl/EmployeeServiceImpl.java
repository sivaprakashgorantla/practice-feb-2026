package com.java8.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java8.demo.exception.NotFoundException;
import com.java8.demo.model.Employee;
import com.java8.demo.repository.EmployeeRepository;
import com.java8.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        employee.setId(null);
        return repository.save(employee);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        return repository.findById(id).map(existing -> {
            existing.setFirstName(employee.getFirstName());
            existing.setLastName(employee.getLastName());
            existing.setEmail(employee.getEmail());
            existing.setAge(employee.getAge());
            existing.setDepartment(employee.getDepartment());
            existing.setPosition(employee.getPosition());
            existing.setSalary(employee.getSalary());
            existing.setHireDate(employee.getHireDate());
            existing.setFullTime(employee.isFullTime());
            return repository.save(existing);
        }).orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Employee not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public List<Employee> findByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    @Override
    public List<Employee> findByMinAge(int minAge) {
        return repository.findByAgeGreaterThanEqual(minAge);
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}

