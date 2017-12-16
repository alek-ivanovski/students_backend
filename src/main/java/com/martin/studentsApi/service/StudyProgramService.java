package com.martin.studentsApi.service;

import com.martin.studentsApi.model.StudyProgram;

public interface StudyProgramService {

    Iterable<StudyProgram> findAllStudyPrograms();

    StudyProgram saveStudyProgram(String name);

    void deleteStudyProgramById(Long id);

    StudyProgram editStudyProgram(Long id, String name);
}
