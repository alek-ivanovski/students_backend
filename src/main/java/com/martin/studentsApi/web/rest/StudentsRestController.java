package com.martin.studentsApi.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.martin.studentsApi.json.StudentDeserializer;
import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudentResource;
import com.martin.studentsApi.model.exceptions.StudentNotFoundException;
import com.martin.studentsApi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class StudentsRestController {

    @Autowired
    private StudentService service;

    @GetMapping(value = "{id}")
    public StudentResource getStudent(@PathVariable String id) {
        return new StudentResource(service.findStudentById(id));
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) throws IOException {
       Student result = this.service.addStudent(student);
        // get the location on which the new resource is created
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }

    @PutMapping(value = "{id}")
    public Student overrideStudent(@RequestBody Student student, @PathVariable String id) {
        return this.service.overrideStudent(id, student);
    }

    @PatchMapping(value = "{id}")
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
    public Iterable<StudentResource> getAllStudents() {
        List<StudentResource> studentResourceList =
                StreamSupport.stream(service.getAllStudents().spliterator(), false)
                .map(StudentResource::new)
                .collect(Collectors.toList());
        return new Resources<>(studentResourceList);
    }
}
