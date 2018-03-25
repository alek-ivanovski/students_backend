package com.martin.studentsApi.service;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student findStudentById(String id);

    Student addStudent(Student student);

    Student overrideStudent(String id, Student student);

    Student updateStudent(String id, Student student);

    void deleteStudentById(String id);

    Iterable<Student> getAllStudents();

    void validateStudent(String id);

    void validateStudentIdFormat(String id);
}
