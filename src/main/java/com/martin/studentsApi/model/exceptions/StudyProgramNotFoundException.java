package com.martin.studentsApi.model.exceptions;

public class StudyProgramNotFoundException extends RuntimeException {

    public StudyProgramNotFoundException(String id) {
        super("could not find study program with id '" + id + "'");
    }
}
