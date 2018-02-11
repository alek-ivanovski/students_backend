package com.martin.studentsApi.persistence;

import com.martin.studentsApi.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StudentDAO extends CrudRepository<Student, Long> {

    Optional<Student> findById(Long id);
    List<Student> findByStudyProgramId(Long id);
}
