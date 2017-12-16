package com.martin.studentsApi.web.rest;

import com.martin.studentsApi.model.StudyProgram;
import com.martin.studentsApi.model.exceptions.StudyProgramNotExistException;
import com.martin.studentsApi.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/api/study-programs", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudyProgramsRestController {

    private StudyProgramService service;

    @Autowired
    public StudyProgramsRestController(StudyProgramService service) {
        this.service = service;
    }

    @RequestMapping(method = GET)
    public Iterable<StudyProgram> getAllStudyPrograms() {
        return service.findAllStudyPrograms();
    }

    @RequestMapping(value = "{id}", method = DELETE)
    public ResponseEntity deleteStudyProgram(@PathVariable String id) {
        try {
            service.deleteStudyProgramById(Long.parseLong(id));
            return new ResponseEntity(HttpStatus.OK);
        } catch (StudyProgramNotExistException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = POST)
    public ResponseEntity addStudyProgram(@RequestParam("studyProgramName") String name) {
        try {
            StudyProgram sp = service.saveStudyProgram(name);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .location(URI.create("http://localhost:8080/studentsApi/study_programs/" + sp.getId()))
                    .body(sp);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Study Program with the name '" + name + "' already exists");
        }
    }

    @RequestMapping(value = "{id}", method = PATCH)
    public ResponseEntity editStudyProgram(@PathVariable String id,
                                           @RequestParam String studyProgramName) {
        try {
            service.editStudyProgram(Long.parseLong(id), studyProgramName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (StudyProgramNotExistException | NumberFormatException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
