package com.martin.studentsApi.model.exceptions;

public class StudentIndexMismatchException extends RuntimeException {

    public StudentIndexMismatchException(String reqUrlIndex, String reqBodyIndex) {
        super("Student index '" + reqUrlIndex + "' provided in the url path does not match the student index '" + reqBodyIndex + "' provided in the request body");
    }
}
