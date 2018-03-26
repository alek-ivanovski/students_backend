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

import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"test", "repo"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StudyProgramDAOTest {

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

        StudyProgram kni = new StudyProgram(1L, "KNI");
        StudyProgram pet = new StudyProgram(2L, "PET");
        this.studyProgramDAO.save(kni);
        this.studyProgramDAO.save(pet);
        Student s1 = new Student(1L, "Martin", "Kotevski", kni);
        Student s2 = new Student(2L, "Alek", "Ivanovski", kni);
        Student s3 = new Student(3L, "Kostadin", "Kocev", kni);
        this.studentDAO.save(s1);
        this.studentDAO.save(s2);
        this.studentDAO.save(s3);

        // TODO Learn what do the following do
        // entityManager.flush();
        // entityManager.clear();
    }

    @Test
    public void testFindByIdUsingEntityGraph() {
        StudyProgram sp = this.studyProgramDAO.findByIdUsingEntityGraph(1L).get();
        Assert.assertEquals(1L, sp.getId().longValue());
    }

    @Test
    public void testFindById() {
        StudyProgram sp = this.studyProgramDAO.findById(1L).get();
        Assert.assertEquals(1L, sp.getId().longValue());
    }

}