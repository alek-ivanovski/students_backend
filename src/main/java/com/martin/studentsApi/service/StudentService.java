package com.martin.studentsApi.service;

import com.martin.studentsApi.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Iterable<Student> findAllStudents();

    Student findStudentById(Long id);

    Student saveStudent(Long id, String firstName, String lastName, String studyProgramName);

    void deleteStudentById(Long id);

    Student editStudent(Long index, Optional<String> firstName, Optional<String> lastName, Optional<String> studyProgramName);

    List<Student> findStudentsByStudyProgramId(Long id);

    boolean isValidId(Long id);
}
