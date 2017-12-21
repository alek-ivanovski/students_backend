package com.martin.studentsApi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Student does not exist")
public class StudentNotExistException extends RuntimeException {
}
