package com.martin.studentsApi.model.exceptions;

public class StudyProgramIdMismatchException extends RuntimeException {

    public StudyProgramIdMismatchException(String reqUrlIndex, String reqBodyIndex) {
        super("Study Program id '" + reqUrlIndex + "' provided in the request url path does not match the study program id '" + reqBodyIndex + "' provided in the request body");
    }
}
