package com.martin.studentsApi.persistence;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@DataJpaTest
public class StudentDAOTest {}