package com.martin.studentsApi.service.impl;

import com.martin.studentsApi.model.StudyProgram;
import com.martin.studentsApi.model.exceptions.StudyProgramNotExistException;
import com.martin.studentsApi.persistence.StudyProgramDAO;
import com.martin.studentsApi.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class StudyProgramServiceImpl implements StudyProgramService {

    private StudyProgramDAO studyProgramDAO;

    @Autowired
    public StudyProgramServiceImpl(StudyProgramDAO repository) {
        this.studyProgramDAO = repository;
    }

    @Override
    public Iterable<StudyProgram> findAllStudyPrograms() {
        return studyProgramDAO.findAll();
    }

    @Override
    public StudyProgram findStudyProgramById(Long id) {
        return this.studyProgramDAO.findById(id)
                .orElseThrow(StudyProgramNotExistException::new);
    }

    @Override
    public void deleteStudyProgramById(Long id) {
        studyProgramDAO.delete(studyProgramDAO.findById(id)
                .orElseThrow(StudyProgramNotExistException::new));
    }

//    @Override
//    public StudyProgram saveStudyProgram(String name) throws DataIntegrityViolationException {
//        StudyProgram sp = new StudyProgram(name);
//        return studyProgramDAO.save(sp);
//    }

    @Override
    public StudyProgram saveStudyProgram(StudyProgram studyProgram) throws DataIntegrityViolationException {
        String name = studyProgram.getName();
        return this.studyProgramDAO.save(new StudyProgram(name));
    }

    @Override
    public StudyProgram updateStudyProgram(Long id, StudyProgram studyProgram) {
        if (id.equals(studyProgram.getId())) {
            studyProgramDAO.findById(id)
                    .orElseThrow(StudyProgramNotExistException::new);
            return this.studyProgramDAO.save(studyProgram);
        } else {
            // TODO create new exception for this case
            throw new StudyProgramNotExistException();
        }
    }

//    @Override
//    public StudyProgram updateStudyProgram(Long id, String name) {
//        StudyProgram sp = studyProgramDAO.findById(id)
//                .orElseThrow(StudyProgramNotExistException::new);
//        sp.setName(name);
//        return studyProgramDAO.save(sp);
//    }

    @Deprecated
    @Override
    public StudyProgram validateStudyProgram(StudyProgram studyProgram) {
        StudyProgram sp = this.studyProgramDAO.findById(studyProgram.getId())
                .orElseThrow(StudyProgramNotExistException::new);
        if (!sp.getName().equals(studyProgram.getName())) {
            throw new StudyProgramNotExistException();
        }
        return sp;
    }


}
