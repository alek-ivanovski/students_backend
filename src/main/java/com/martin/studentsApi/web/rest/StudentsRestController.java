package com.martin.studentsApi.web.rest;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;
import com.martin.studentsApi.model.exceptions.InvalidIndexFormatException;
import com.martin.studentsApi.model.exceptions.StudentNotExistException;
import com.martin.studentsApi.model.exceptions.StudyProgramNotExistException;
import com.martin.studentsApi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class StudentsRestController {

    private StudentService service;

    @Autowired
    public StudentsRestController(StudentService service) {
        this.service = service;
    }

    @RequestMapping(method = GET)
    public Iterable<Student> getAllStudents() {
        return service.findAllStudents();
    }

    @RequestMapping(value = "{id}", method = GET)
    public ResponseEntity<Student> getStudent(@PathVariable String id) {
        Student student;
        try {
            student = service.findStudentById(Long.parseLong(id));
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (StudentNotExistException | NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "by-study-program/{id}", method = GET)
    public Iterable<Student> getStudentByStudyProgram(@PathVariable String id) {
        return service.findStudentsByStudyProgramId(Long.parseLong(id));
    }

    @RequestMapping(value = "{id}", method = DELETE)
    public ResponseEntity<Integer> deleteStudent(@PathVariable String id) {
        try {
            service.deleteStudentById(Long.parseLong(id));
            return ResponseEntity.status(HttpStatus.OK).body(0);
        } catch (StudentNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }


    @RequestMapping(value = "{id}", method = PATCH)
    public Student editStudent(@RequestBody Student student, @PathVariable String id) {
        return this.service.updateStudent(Long.parseLong(id), student);
    }

    @RequestMapping(method = POST)
    public Student addStudent(@RequestBody Student student) {
        return this.service.saveStudent(student);
    }


}
