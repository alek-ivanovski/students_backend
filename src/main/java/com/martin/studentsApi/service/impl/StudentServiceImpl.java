package com.martin.studentsApi.service.impl;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;
import com.martin.studentsApi.model.exceptions.InvalidIndexFormatException;
import com.martin.studentsApi.model.exceptions.StudentAlreadyExistsException;
import com.martin.studentsApi.model.exceptions.StudentNotExistException;
import com.martin.studentsApi.model.exceptions.StudyProgramNotExistException;
import com.martin.studentsApi.persistence.StudentDAO;
import com.martin.studentsApi.persistence.StudyProgramDAO;
import com.martin.studentsApi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.martin.studentsApi.service.StudyProgramService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentDAO studentDAO;
    private StudyProgramDAO studyProgramDAO;
    private StudyProgramService studyProgramService;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO,
                              StudyProgramDAO studyProgramDAO,
                              StudyProgramService studyProgramService) {
        this.studentDAO = studentDAO;
        this.studyProgramDAO = studyProgramDAO;
        this.studyProgramService = studyProgramService;
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

//    @Override
//    public Student saveStudent(Student student) {
//        return this.studentDAO.save(student);
//    }

//    @Override
//    public Student saveStudent(Long id,
//                              String firstName,
//                              String lastName,
//                              String studyProgramName) {
//        if (!isValidId(id))
//            throw new InvalidIndexFormatException();
//        StudyProgram studyProgram = studyProgramDAO
//                .findByName(studyProgramName)
//                .orElseThrow(StudyProgramNotExistException::new);
//        Student student = new Student(id, firstName, lastName, studyProgram);
//        this.studentDAO.save(student);
//        return student;
//    }

    @Override
    public Student saveStudent(Student student) {
        this.validateStudentId(student.getId());
        this.studentDAO.findById(student.getId())
                .ifPresent(s -> {throw  new StudentAlreadyExistsException();});
        this.studyProgramService.validateStudyProgram(student.getStudyProgram());
        this.studentDAO.save(student);
        return student;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        this.validateStudentId(id);
        if (!id.equals(student.getId())) {

        }
        studentDAO.findById(student.getId())
                .orElseThrow(StudentNotExistException::new);
        this.studyProgramService.validateStudyProgram(student.getStudyProgram());
        return this.studentDAO.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentDAO.delete(studentDAO.findById(id)
                .orElseThrow(StudentNotExistException::new));
    }

    @Override
    public List<Student> findStudentsByStudyProgramId(Long id) {
        if (!studyProgramDAO.exists(id))
            throw new StudyProgramNotExistException();
        return studentDAO.findByStudyProgramId(id);
    }

    @Override
    public void validateStudentId(Long id) {
        if (!id.toString().matches("\\d{6}")) {
            throw new InvalidIndexFormatException();
        }
    }

}
