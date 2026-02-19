package com.java8.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java8.demo.exception.NotFoundException;
import com.java8.demo.model.Student;
import com.java8.demo.repository.StudentRepository;
import com.java8.demo.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Student save(Student student) {
        student.setId(null); // ensure create
        return repository.save(student);
    }

    @Override
    public Student update(Long id, Student student) {
        return repository.findById(id).map(existing -> {
            existing.setName(student.getName());
            existing.setAge(student.getAge());
            existing.setGender(student.getGender());
            existing.setMarks(student.getMarks());
            return repository.save(existing);
        }).orElseThrow(() -> new NotFoundException("Student not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
