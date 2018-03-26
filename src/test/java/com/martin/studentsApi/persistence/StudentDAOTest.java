package com.martin.studentsApi.persistence;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.JoinType;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.data.jpa.domain.Specifications.where;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"test", "repo"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StudentDAOTest {

    private static Logger logger = LoggerFactory.getLogger(StudentDAOTest.class);
    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private StudyProgramDAO studyProgramDAO;

    // TODO will need this with real (non in-memory) database??
    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setUp() {

//        Initializing study programs
        StudyProgram kni = new StudyProgram(1L, "KNI");
        StudyProgram pet = new StudyProgram(2L, "PET");
        this.studyProgramDAO.save(kni);
        this.studyProgramDAO.save(pet);

//        Initializing students
        Student s1 = new Student(1L, "Martin", "Kotevski", kni);
        Student s2 = new Student(2L, "Alek", "Ivanovski", kni);
        Student s3 = new Student(3L, "Kostadin", "Kocev", kni);
        Student s4 = new Student(4L, "Filip", "Simonovski", pet);
        this.studentDAO.save(s1);
        this.studentDAO.save(s2);
        this.studentDAO.save(s3);

        // TODO Learn what do the following do
        // entityManager.flush();
        // entityManager.clear();
    }

    @Test
    public void testFindStudentsWithSpecification() {

        Specification<Student> byLastNameLike = (root, qb, cb) ->
                cb.like(root.get("lastName"), "%ski");
        Specification<Student> byStudentProgram = (root, qb, cb) ->
                cb.equal(root.join("studyProgram", JoinType.INNER).get("name"), "KNI");
        Specifications spec = Specifications.where(byLastNameLike).and(byStudentProgram);
        List<Student> students = studentDAO.findAll(spec);

//        Initializing expected value
        StudyProgram kni = new StudyProgram(1L, "KNI");
        Student expected = new Student(2L, "Alek", "Ivanovski", kni);
        Student expected2 = new Student(1L, "Martin", "Kotevski", kni);

        Assert.assertEquals(expected.getId(), students.get(1).getId());
        Assert.assertEquals(expected2.getId(), students.get(0).getId());
        System.out.print(students.get(0).getLastName());
        System.out.print(students.get(1).getLastName());
        Assert.assertEquals(2, students.size());

    }

}