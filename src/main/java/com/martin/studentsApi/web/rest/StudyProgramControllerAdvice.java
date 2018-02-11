package com.martin.studentsApi.web.rest;

import com.martin.studentsApi.model.exceptions.StudentIndexMismatchException;
import com.martin.studentsApi.model.exceptions.StudyProgramIdMismatchException;
import com.martin.studentsApi.model.exceptions.StudyProgramNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StudyProgramControllerAdvice {

    @ExceptionHandler(StudyProgramNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody VndErrors.VndError
    studyProgramNotFoundExceptionHandler(StudyProgramNotFoundException ex) {
        return new VndErrors.VndError("error", ex.getMessage());
    }

    @ExceptionHandler(StudyProgramIdMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody VndErrors.VndError
    studyProgramIndexMismatchExeptionHandler(StudyProgramIdMismatchException ex) {
        return new VndErrors.VndError("error", ex.getMessage());
    }

}
