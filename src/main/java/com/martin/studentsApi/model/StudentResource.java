package com.martin.studentsApi.model;

import com.martin.studentsApi.web.rest.StudentsRestController;
import com.martin.studentsApi.web.rest.StudyProgramsRestController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


public class StudentResource extends ResourceSupport {

    private final Student student;

    public StudentResource(Student student) {
        this.student = student;
        this.add(linkTo(StudentsRestController.class)
                .slash(student.getId()).withSelfRel());
        this.add(linkTo(StudentsRestController.class).withRel("students"));
        if (student.getStudyProgram() != null)
            this.add(linkTo(StudyProgramsRestController.class)
                    .slash(student.getStudyProgram().getId()).withRel("studyProgram"));
    }

    public Long getIndex() {
        return this.student.getId();
    }

    public String getFirstName() {
        return this.student.getFirstName();
    }

    public String getLastName() {
        return this.student.getLastName();
    }

    public String getStudyProgramName() {
        StudyProgram sp = this.student.getStudyProgram();
        if (sp == null) return null;
        else return sp.getName();
    }
}
