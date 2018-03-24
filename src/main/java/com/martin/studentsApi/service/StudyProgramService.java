package com.martin.studentsApi.service;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;

public interface StudyProgramService {

    StudyProgram getStudyProgramById(String id);

    StudyProgram saveStudyProgram(StudyProgram studyProgram);

    StudyProgram updateStudyProgram(String id, StudyProgram studyProgram);

    void deleteStudyProgramById(String id);

    Iterable<StudyProgram> getAllStudyPrograms();

    Iterable<Student> getStudentsByStudyProgramId(String id);

    StudyProgram validateStudyProgram(StudyProgram studyProgram);
}
