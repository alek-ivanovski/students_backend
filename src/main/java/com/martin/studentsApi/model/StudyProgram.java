package com.martin.studentsApi.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.List;



@NamedEntityGraph(
        name = "graph.StudyProgram.students",
        attributeNodes = {
                @NamedAttributeNode("students")
        })
@Entity
@Table(name = "study_programs")
public class StudyProgram {

    public StudyProgram() {} // JPA only

    public StudyProgram(String name) {
        this.name = name;
    }

    public StudyProgram(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
//    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "studyProgram")
    public List<Student> students;

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
