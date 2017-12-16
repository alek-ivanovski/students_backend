package com.martin.studentsApi.service.impl;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;
import com.martin.studentsApi.model.exceptions.InvalidIndexFormatException;
import com.martin.studentsApi.model.exceptions.StudentNotExistException;
import com.martin.studentsApi.model.exceptions.StudyProgramNotExistException;
import com.martin.studentsApi.persistence.StudentDAO;
import com.martin.studentsApi.persistence.StudyProgramDAO;
import com.martin.studentsApi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentDAO studentDAO;
    private StudyProgramDAO studyProgramDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO,
                              StudyProgramDAO studyProgramDAO) {
        this.studentDAO = studentDAO;
        this.studyProgramDAO = studyProgramDAO;
    }

    @Override
    public Iterable<Student> findAllStudents() {
        return studentDAO.findAll();
    }

    @Override
    public Student findStudentById(Long id) {
        return studentDAO.findById(id)
                .orElseThrow(StudentNotExistException::new);
    }

    @Override
    public Student saveStudent(Long id,
                              String firstName,
                              String lastName,
                              String studyProgramName) {
        if (!isValidId(id))
            throw new InvalidIndexFormatException();
        StudyProgram studyProgram = studyProgramDAO
                .findByName(studyProgramName)
                .orElseThrow(StudyProgramNotExistException::new);
        Student student = new Student(id, firstName, lastName, studyProgram);
        this.studentDAO.save(student);
        return student;
    }

    @Override
    public void deleteStudentById(Long id) {
        studentDAO.delete(studentDAO.findById(id)
                .orElseThrow(StudentNotExistException::new));
    }


    @Override
    public Student editStudent(Long id, Optional<String> firstName, Optional<String> lastName, Optional<String> studyProgramName) {
        Student student = studentDAO.findById(id)
                .orElseThrow(StudentNotExistException::new);
        studyProgramName.ifPresent(studyProgram -> {
            StudyProgram sp = studyProgramDAO.findByName(studyProgram)
                    .orElseThrow(StudyProgramNotExistException::new);
            student.setStudyProgram(sp);
        });
        firstName.ifPresent(student::setFirstName);
        lastName.ifPresent(student::setLastName);
        return studentDAO.save(student);
    }

    @Override
    public List<Student> findStudentsByStudyProgramId(Long id) {
        if (!studyProgramDAO.exists(id))
            throw new StudyProgramNotExistException();
        return studentDAO.findByStudyProgramId(id);
    }

    @Override
    public boolean isValidId(Long id) {
        return id.toString().matches("\\d{6}");
    }


}
