package com.martin.studentsApi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "study_programs")
public class StudyProgram {

    public StudyProgram() {} // JPA only

    public StudyProgram(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "studyProgram")
    private List<Student> students;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

}
