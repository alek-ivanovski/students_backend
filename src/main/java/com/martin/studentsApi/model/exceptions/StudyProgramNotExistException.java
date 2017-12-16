package com.martin.studentsApi.model.exceptions;

public class StudyProgramNotExistException extends RuntimeException {

    private String msg;

    public StudyProgramNotExistException(String msg) {
        super(msg);
    }

    public StudyProgramNotExistException() {}

    @Override
    public String getMessage() {
        return this.msg;
    }
}
