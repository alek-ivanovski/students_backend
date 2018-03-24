package com.martin.studentsApi.web.rest;

import com.martin.studentsApi.model.exceptions.InvalidStudentIndexFormatException;
import com.martin.studentsApi.model.exceptions.StudentAlreadyExistsException;
import com.martin.studentsApi.model.exceptions.StudentIndexMismatchException;
import com.martin.studentsApi.model.exceptions.StudentNotFoundException;
import org.springframework.hateoas.VndErrors.VndError;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StudentControllerAdvice {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody VndError
    studentNotFoundExceptionHandler(StudentNotFoundException ex) {
        return new VndError("error", ex.getMessage());
    }

    @ExceptionHandler(InvalidStudentIndexFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody VndError
    invalidStudentIndexFormatExceptionHandler(InvalidStudentIndexFormatException ex) {
        return new VndError("error", ex.getMessage());
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody VndError
    studentAlreadyExistsExceptionHandler(StudentAlreadyExistsException ex) {
        return new VndError("error", ex.getMessage());
    }

    @ExceptionHandler(StudentIndexMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody VndError
    studentIndexMismatchExeptionHandler(StudentIndexMismatchException ex) {
        return new VndError("error", ex.getMessage());
    }
}
