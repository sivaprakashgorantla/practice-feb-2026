package com.java8.demo.loader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.java8.demo.StudentOperations;
import com.java8.demo.model.Student;
import com.java8.demo.repository.StudentRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final StudentOperations studentOperations;

    public DataInitializer(StudentRepository studentRepository, StudentOperations studentOperations) {
        this.studentRepository = studentRepository;
        this.studentOperations = studentOperations;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (studentRepository.count() > 0) {
            System.out.println("Students already initialized: " + studentRepository.count());
            return;
        }

        List<Student> students = IntStream.rangeClosed(1, 15)
                .mapToObj(i -> new Student(
                        sampleName(i),
                        18 + (i % 6),
                        (i % 2 == 0) ? "M" : "F",
                        sampleMarks(i)
                ))
                .collect(Collectors.toList());

        studentRepository.saveAll(students);

        System.out.println("Initialized " + studentRepository.count() + " students into H2 database.");

        // run additional operations (pass/fail segregation)
        studentOperations.operations();
    }

    private static String sampleName(int i) {
        String[] names = {"Siva","Alice", "Bob", "Carlos", "Diana", "Eve", "Frank", "Grace", "Hannah", "Ian", "Judy", "Karl", "Lina", "Mike", "Nina", "Oscar","Aruna"};
        return names[(i - 1) % names.length] + " " + i;
    }

    private static List<Integer> sampleMarks(int i) {
        // create 3 marks; make every 5th student clearly fail by giving low marks
        if (i % 5 == 0) {
            // failing marks (avg well below pass threshold)
            // use fixed low marks for clarity and to avoid unnecessary static-analysis warnings
            return Arrays.asList(18, 20, 12);
        }
        // otherwise generate normal marks
        return Arrays.asList(60 + (i * 2) % 40, 65 + (i * 3) % 35, 70 + (i * 5) % 30);
    }
}
