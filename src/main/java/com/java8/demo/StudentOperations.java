package com.java8.demo;

import java.util.Comparator;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.java8.demo.model.Student;
import com.java8.demo.service.StudentService;

@Component
public class StudentOperations {

    private final StudentService studentService;

    public StudentOperations(StudentService studentService) {
        this.studentService = studentService;
    }

    public void operations() {
        System.out.println("Performing student operations...");


        List<Student> students = studentService.findAll();
        if (students == null || students.isEmpty()) {
            System.out.println("No students available to evaluate.");
            return;
        }

        // Add your student operations logic here
        //sagrigatePassAndFailStudentsUsingPartitioningBy(students);
        //sagrigatePassAndFailStudents(students);
       // listOfStudentsToMapStudents(students);
        //listOfStudentsStartWithA(students);
        //studentsSortByKay(students);
        //studentsSortByValueStudentName(students);
       // studentsSortByValueAge(students);
    }

    private void studentsSortByValueAge(List<Student> students) {
        System.out.println("List of students Sorting by Age");
        students
                .stream()
                .collect(Collectors.toMap(
                        Student::getId,
                        Function.identity(),
                        (s1, s2) -> s1,
                        LinkedHashMap::new
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Student>comparingByValue(Comparator.comparingInt(Student::getAge)))
                .forEach(entry ->
                        System.out.println("Student sorted by Age: " + entry.getValue()));

    }

    private void studentsSortByValueStudentName(List<Student> students) {
        System.out.println("List of students Before sorting");

            students.forEach(s -> System.out.println("Student: " + s));
        System.out.println("List of students After sorting");

        students.stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toMap(
                        Student::getId,
                        Function.identity(),
                        (s1, s2) -> s1,
                        LinkedHashMap::new
                ))
                .forEach((id, student) ->
                        System.out.println("Student sorted by Name: " + student));
    }

    private void studentsSortByKay(List<Student> students) {
/*        students.stream()
                .sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toMap(
                        Student::getId,
                        Function.identity(),
                        (s1,s2) -> s1,
                LinkedHashMap::new))
                .forEach((id, student) -> System.out.println("Student sorted by ID: " + student));
    */
        students.stream()
                //.sorted(Comparator.comparing(Student::getId)) // sort first
                .collect(Collectors.toMap(
                        Student::getId,
                        Function.identity(),
                        (s1, s2) -> s1,
                        LinkedHashMap::new
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Student>comparingByKey())
                .forEach(entry ->
                        System.out.println("Student sorted by ID: " + entry.getValue()));
    }

    private void listOfStudentsStartWithA(List<Student> students) {
        students.stream()
                .filter(s -> s.getName() != null && s.getName().startsWith("A"))
                .forEach(s -> System.out.println("Student name starts with A: " + s));
    }

    private void listOfStudentsToMapStudents(List<Student> students) {
        students.stream()
                .collect(Collectors.toMap(Student::getId, s -> s))
                .forEach((id, student) -> System.out.println("ID: " + id + ", Student: " + student));
    }

    private void sagrigatePassAndFailStudents(List<Student> students) {
        System.out.println("sagrigatePassAndFailStudents  ------------------------------------------..");


        List<Student> partitioned = students.stream().filter(Student::isPass).
                collect(Collectors.toList());


        List<Student> passed =students.stream().filter(Student::isPass).
                collect(Collectors.toList());
        List<Student> failed =students.stream().filter(student -> !student.isPass()).
                collect(Collectors.toList());

        System.out.println("sagrigatePassAndFailStudents Passed count: " + passed.size());
        passed.forEach(s -> System.out.println("  PASS: " + s));

        System.out.println("sagrigatePassAndFailStudents Failed count: " + failed.size());
        failed.forEach(s -> System.out.println("  FAIL: " + s));
    }


    private void sagrigatePassAndFailStudentsUsingPartitioningBy(List<Student> students) {
        System.out.println("Segregating pass and fail students using partitioningBy...");

        // Define pass criteria: average marks >= 40
        Map<Boolean, List<Student>> partitioned = students  .stream()
                .collect(Collectors.partitioningBy(s -> {
                    List<Integer> marks = s.getMarks();
                    double avg = 0.0;
                    if (marks != null && !marks.isEmpty()) {
                        avg = marks.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                    }
                    return avg >= 40.0;
                }));

        List<Student> passed = partitioned.getOrDefault(true, List.of());
        List<Student> failed = partitioned.getOrDefault(false, List.of());

        System.out.println("Passed count: " + passed.size());
        passed.forEach(s -> System.out.println("  PASS: " + s));

        System.out.println("Failed count: " + failed.size());
        failed.forEach(s -> System.out.println("  FAIL: " + s));
    }
}
