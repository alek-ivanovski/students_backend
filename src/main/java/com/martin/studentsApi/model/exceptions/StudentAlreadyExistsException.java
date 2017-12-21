package com.martin.studentsApi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Student with that index already exists")
public class StudentAlreadyExistsException extends RuntimeException {
}
