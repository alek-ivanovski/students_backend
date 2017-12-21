package com.martin.studentsApi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Study program does not exist")
public class StudyProgramNotExistException extends RuntimeException {

//    private String msg;

    public StudyProgramNotExistException(String msg) {
        super(msg);
    }

    public StudyProgramNotExistException() {}

//    @Override
//    public String getMessage() {
//        return this.msg;
//    }
}
