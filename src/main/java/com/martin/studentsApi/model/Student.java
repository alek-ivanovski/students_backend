package com.martin.studentsApi.model;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    public Student() {}

    public Student(Long id, String firstName, String lastName, StudyProgram studyProgram) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studyProgram = studyProgram;
    }

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "study_program_id")
    private StudyProgram studyProgram;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }
}
