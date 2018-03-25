package com.martin.studentsApi.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.martin.studentsApi.model.Student;
import com.martin.studentsApi.model.StudyProgram;
import com.martin.studentsApi.model.exceptions.StudyProgramNotFoundException;
import com.martin.studentsApi.persistence.StudyProgramDAO;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

public class StudentDeserializer extends JsonDeserializer<Student> {

    @Autowired
    private StudyProgramDAO studyProgramDAO;

    @Override
    public Student deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        final Long id = node.get("id").asLong();
        final String firstName = node.get("firstName").asText();
        final String lastName = node.get("lastName").asText();
        final Long studyProgramId;
        if (node.has("studyProgramId")) studyProgramId = node.get("studyProgramId").asLong();
        else studyProgramId = null;

        StudyProgram sp = null;
        if (studyProgramId != null)
            sp = this.studyProgramDAO.findById(studyProgramId)
                .orElseThrow(() -> new StudyProgramNotFoundException(studyProgramId.toString()));

        Student s = new Student();
        s.setId(id);
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setStudyProgram(sp);
        return s;
    }
}
