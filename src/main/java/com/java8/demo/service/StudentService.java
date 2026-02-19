package com.java8.demo.service;

import java.util.List;
import java.util.Optional;

import com.java8.demo.model.Student;

public interface StudentService {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    Student save(Student student);
    Student update(Long id, Student student);
    void deleteById(Long id);
    long count();
}

