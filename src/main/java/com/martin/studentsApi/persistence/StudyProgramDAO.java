package com.martin.studentsApi.persistence;

import com.martin.studentsApi.model.StudyProgram;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudyProgramDAO extends CrudRepository<StudyProgram, Long> {

    Optional<StudyProgram> findById(Long id);
    Optional<StudyProgram> findByName(String name);
    void deleteById(Long id);
}
