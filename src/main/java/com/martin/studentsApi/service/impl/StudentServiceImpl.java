package com.martin.studentsApi.service.impl;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.exceptions.*;
import com.martin.studentsApi.persistence.StudentDAO;
import com.martin.studentsApi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.martin.studentsApi.service.StudyProgramService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private StudyProgramService studyProgramService;

    @Override
    public Student findStudentById(String id) {
        this.validateStudentIdFormat(id);
        return studentDAO.findById(Long.parseLong(id))
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student addStudent(Student student) {
        // todo make custom validator for the student index
        this.validateStudentIdFormat(student.getId().toString());
        this.studentDAO.findById(student.getId())
                .ifPresent(s ->
                {
                    throw new StudentAlreadyExistsException(s.getId().toString());
                });
        this.studyProgramService.validateStudyProgram(student.getStudyProgram());
        this.studentDAO.save(student);
        return student;
    }

    @Override
    public Student overrideStudent(String id, Student student) {
        this.validateStudent(id);
        if (!id.equals(student.getId().toString()))
            throw new StudentIndexMismatchException(id, student.getId().toString());
        this.studyProgramService.validateStudyProgram(student.getStudyProgram());
        return this.studentDAO.save(student);
    }

    @Override
    public Student updateStudent(String id, Student student) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteStudentById(String id) {
        this.validateStudentIdFormat(id);
        studentDAO.delete(studentDAO
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new StudentNotFoundException(id)));
    }

    @Override
    public Iterable<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    // todo make custom validator for student index
    @Override
    public void validateStudent(String id) {
        this.validateStudentIdFormat(id);
        this.studentDAO
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public void validateStudentIdFormat(String id) {
        if (!id.matches("\\d{6}"))
            throw new InvalidStudentIndexFormatException(id);
    }

}
