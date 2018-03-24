package com.martin.studentsApi.model.exceptions;

public class StudentAlreadyExistsException extends RuntimeException {

    public StudentAlreadyExistsException(String id) {
        super("Student with index '" + id + "' already exists");
    }
}
