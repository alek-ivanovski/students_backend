package com.martin.studentsApi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid index format")
public class InvalidIndexFormatException extends RuntimeException {
}
