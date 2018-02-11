package com.martin.studentsApi.model.exceptions;

public class InvalidStudentIndexFormatException extends RuntimeException {

    public InvalidStudentIndexFormatException(String studentIndex) {
        super("The index '" + studentIndex + "' is not in a valid index format");
    }
}
