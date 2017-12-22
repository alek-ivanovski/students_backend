package com.martin.studentsApi.service;

import com.martin.studentsApi.model.StudyProgram;

public interface StudyProgramService {

    Iterable<StudyProgram> findAllStudyPrograms();

    StudyProgram findStudyProgramById(Long id);

    StudyProgram saveStudyProgram(StudyProgram studyProgram);

    StudyProgram updateStudyProgram(Long id, StudyProgram studyProgram);

    void deleteStudyProgramById(Long id);

    StudyProgram validateStudyProgram(StudyProgram studyProgram);
}
