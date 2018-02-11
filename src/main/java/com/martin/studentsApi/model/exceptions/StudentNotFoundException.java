package com.martin.studentsApi.model.exceptions;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String studentIndex) {
        super("could not find student with index '" + studentIndex + "'");
    }
}
