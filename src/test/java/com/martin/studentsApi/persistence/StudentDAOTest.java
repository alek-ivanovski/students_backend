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
        // Save study programs
        StudyProgram kni = new StudyProgram( 1L, "KNI");
        this.studyProgramDAO.save(kni);
        StudyProgram pet = new StudyProgram( 2L, "PET");
        this.studyProgramDAO.save(pet);

        // Save students
        Student martin = new Student( 1L, "Martin", "Kotevski", kni);
        this.studentDAO.save(martin);
        Student alek = new Student( 2L, "Alek", "Ivanovski", kni);
        this.studentDAO.save(alek);
        Student filip = new Student( 3L, "Filip", "Simonovski", pet);
        this.studentDAO.save(filip);
        Student kosta = new Student( 4L, "Kostadin", "Kocev", kni);
        this.studentDAO.save(kosta);
    }

    @Test
    public void testFindAll() {
        Specification<Student> lastNameEndingWith = (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("lastName"), "%ski");

        Specification<Student> memberInStudyProgramWithName = (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.join("studyProgram", JoinType.INNER).get("name"), "KNI");

        List<Student> students = this.studentDAO.findAll(where(lastNameEndingWith).and(memberInStudyProgramWithName));
        System.out.println(students);
        Assert.assertEquals(2, students.size());
    }

}