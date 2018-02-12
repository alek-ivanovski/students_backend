package com.martin.studentsApi.model;

import com.martin.studentsApi.web.rest.StudyProgramsRestController;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class StudyProgramResource extends ResourceSupport {

    private final StudyProgram studyProgram;

    public StudyProgramResource(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
        Long studyProgramId = studyProgram.getId();
        this.add(linkTo(StudyProgramsRestController.class)
                .slash(studyProgramId).withSelfRel());
        this.add(linkTo(methodOn(StudyProgramsRestController.class)
                .getStudentsByStudyProgram(studyProgramId.toString()))
                .withRel("students"));
        this.add(linkTo(StudyProgramsRestController.class).withRel("studyPrograms"));
    }

    public Long getProgramId() {
        return this.studyProgram.getId();
    }

    public String getName() {
        return this.studyProgram.getName();
    }
}
