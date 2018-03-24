package com.martin.studentsApi.web.rest;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudentResource;
import com.martin.studentsApi.model.StudyProgram;
import com.martin.studentsApi.model.StudyProgramResource;
import com.martin.studentsApi.model.exceptions.StudyProgramNotFoundException;
import com.martin.studentsApi.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/api/study_programs", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class StudyProgramsRestController {

    private StudyProgramService service;

    @Autowired
    public StudyProgramsRestController(StudyProgramService service) {
        this.service = service;
    }

    @GetMapping
    public Resources<StudyProgramResource> getAllStudyPrograms() {
        List<StudyProgramResource> studyProgramResources = StreamSupport
                .stream(service.getAllStudyPrograms().spliterator(), false)
                .map(StudyProgramResource::new)
                .collect(Collectors.toList());
        return new Resources<StudyProgramResource>(studyProgramResources);
    }

    @GetMapping(value = "{id}")
    public Resource<StudyProgramResource> getStudyProgramById(@PathVariable String id) {
        return new Resource<StudyProgramResource>(
                new StudyProgramResource(service.getStudyProgramById(id)));
    }

    @RequestMapping(value = "{id}", method = DELETE)
    public ResponseEntity<?> deleteStudyProgram(@PathVariable String id) {
        try {
            service.deleteStudyProgramById(id);
            return ResponseEntity.status(HttpStatus.OK).body(0);
        } catch (StudyProgramNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot delete study program");
        }
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> addStudyProgram(@RequestBody StudyProgram studyProgram) {
        try {
            StudyProgram sp = service.saveStudyProgram(studyProgram);
            URI locationUri = URI.create("http://localhost:8080/studentsApi/study_programs/" + sp.getId());
            return ResponseEntity.created(locationUri).body(sp);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Study Program with the name '" + studyProgram.getName() + "' already exists");
        }
    }

    @RequestMapping(value = "{id}", method = PATCH)
    public ResponseEntity<StudyProgram> editStudyProgram(@PathVariable String id,
                                                         @RequestBody StudyProgram studyProgram) {
        StudyProgram sp = service.updateStudyProgram(id, studyProgram);
        return ResponseEntity.ok(sp);
    }

    @RequestMapping(value = "{id}/students", method = GET)
    public Resources<StudentResource> getStudentsByStudyProgram(@PathVariable String id) {
        List<StudentResource> studentResourceList = StreamSupport
                .stream(this.service.getStudentsByStudyProgramId(id).spliterator(), false)
                .map(StudentResource::new)
                .collect(Collectors.toList());
        return new Resources<StudentResource>(studentResourceList);
    }

}
