package com.martin.studentsApi.web.rest;

import com.martin.studentsApi.model.Student;
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
//@CrossOrigin(origins = "http://localhost:4200")
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

    @RequestMapping(method = POST)
    public ResponseEntity addStudent(@RequestParam("id") String id,
                                     @RequestParam("firstName") String firstName,
                                     @RequestParam("lastName") String lastName,
                                     @RequestParam("studyProgramName") String studyProgramName) {
        try {
            Student student
                    = service.saveStudent(Long.parseLong(id), firstName, lastName, studyProgramName);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .location(URI.create("http://localhost:8080/studentsApi/students/" + id))
                    .body(student);
        } catch (StudyProgramNotExistException
                | InvalidIndexFormatException
                | NumberFormatException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "by-study-program/{id}", method = GET)
    public ResponseEntity<Iterable<Student>> getStudentByStudyProgram(@PathVariable String id) {
        try {
            Iterable<Student> students
                    = service.findStudentsByStudyProgramId(Long.parseLong(id));
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (StudyProgramNotExistException | NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = DELETE)
    public ResponseEntity deleteStudent(@PathVariable String id) {
        try {
            service.deleteStudentById(Long.parseLong(id));
            return new ResponseEntity(HttpStatus.OK);
        } catch (StudentNotExistException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

//    @RequestMapping(value = "{id}", method = PATCH)
//    public ResponseEntity editStudent(@RequestParam("firstName") Optional<String> fname,
//                                      @RequestParam("lastName") Optional<String> lname,
//                                      @RequestParam("studyProgramName") Optional<String> spName,
//                                      @PathVariable("id") String id) {
//        try {
//            service.editStudent(Long.parseLong(id), fname, lname, spName);
//            return new ResponseEntity(HttpStatus.OK);
//        } catch (StudentNotExistException | NumberFormatException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        } catch (StudyProgramNotExistException e) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//    }

    @RequestMapping(value = "{id}", method = PATCH)
    public ResponseEntity editStudent(@RequestBody Student student, @PathVariable String id) {
        System.out.println(student.getFirstName()+student.getLastName()+student.getStudyProgram().getName());
        this.service.saveStudent(student);
        return new ResponseEntity(HttpStatus.OK);
    }


}
