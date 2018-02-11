package com.martin.studentsApi.web.rest;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.exceptions.StudentNotFoundException;
import com.martin.studentsApi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class StudentsRestController {

    private static final String DOMAIN_NAME = "http://localhost:8080/";

    @Autowired
    private StudentService service;

    @GetMapping(value = "{id}")
    public Student getStudent(@PathVariable String id) {
        return service.findStudentById(id);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student result = this.service.addStudent(student);
        // get the location on which the new resource is created
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    @RequestMapping(value = "{id}", method = PUT)
    public Student overrideStudent(@RequestBody Student student, @PathVariable String id) {
        return this.service.overrideStudent(id, student);
    }

    @RequestMapping(value = "{id}", method = PATCH)
    public Student updateStudent(@RequestBody Student student, @PathVariable String id) {
        return this.service.updateStudent(id, student);
    }

    @RequestMapping(value = "{id}", method = DELETE)
    public ResponseEntity<Integer> deleteStudent(@PathVariable String id) {
        try {
            service.deleteStudentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(0);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    @GetMapping
    public Iterable<Student> getAllStudents() {
        return service.getAllStudents();
    }
}
