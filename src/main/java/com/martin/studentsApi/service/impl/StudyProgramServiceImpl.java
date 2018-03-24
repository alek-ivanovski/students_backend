package com.martin.studentsApi.service.impl;

import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;
import com.martin.studentsApi.model.exceptions.StudentNotFoundException;
import com.martin.studentsApi.model.exceptions.StudyProgramIdMismatchException;
import com.martin.studentsApi.model.exceptions.StudyProgramNotFoundException;
import com.martin.studentsApi.persistence.StudentDAO;
import com.martin.studentsApi.persistence.StudyProgramDAO;
import com.martin.studentsApi.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class StudyProgramServiceImpl implements StudyProgramService {

    @Autowired
    private StudyProgramDAO studyProgramDAO;
    @Autowired
    private StudentDAO studentDAO;

    @Override
    public Iterable<StudyProgram> getAllStudyPrograms() {
        return studyProgramDAO.findAll();
    }

    @Override
    public StudyProgram getStudyProgramById(String id) {
        return this.studyProgramDAO.findById(Long.parseLong(id))
                .orElseThrow(() -> new StudyProgramNotFoundException(id));
    }

    @Override
    public void deleteStudyProgramById(String id) {
        studyProgramDAO.delete(studyProgramDAO.findById(Long.parseLong(id))
                .orElseThrow(() -> new StudentNotFoundException(id)));
    }

    @Override
    public StudyProgram saveStudyProgram(StudyProgram studyProgram) throws DataIntegrityViolationException {
        String name = studyProgram.getName();
        return this.studyProgramDAO.save(new StudyProgram(name));
    }

    @Override
    public StudyProgram updateStudyProgram(String id, StudyProgram studyProgram) {
//        this.validateStudyProgram(id);
        if (!id.equals(studyProgram.getId().toString()))
            throw new StudyProgramIdMismatchException(id, studyProgram.getId().toString());
        return this.studyProgramDAO.save(studyProgram);
    }

    @Override
    public Iterable<Student> getStudentsByStudyProgramId(String id) {
        StudyProgram sp = studyProgramDAO.findById(Long.parseLong(id))
                .orElseThrow(() -> new StudyProgramNotFoundException(id));
        return studentDAO.findByStudyProgramId(sp.getId());
    }

    @Deprecated
    @Override
    public StudyProgram validateStudyProgram(StudyProgram studyProgram) {
        throw new NotImplementedException();
    }


}
