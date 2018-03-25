package com.martin.studentsApi.persistence;

import com.martin.studentsApi.model.StudyProgram;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


@Profile("repo")
public interface StudyProgramDAO extends CrudRepository<StudyProgram, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"students"})
    @Query("SELECT study_program FROM #{#entityName} study_program  WHERE study_program.id = ?1")
    Optional<StudyProgram> findByIdUsingEntityGraph(Long id);

    Optional<StudyProgram> findById(Long id);
    Optional<StudyProgram> findByName(String name);
}
