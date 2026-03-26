package com.gvi.project.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionEntityTest {

    @Test
    void defaultConstructorShouldInitializeDefaults() {
        QuestionEntity entity = new QuestionEntity();

        assertFalse(entity.getAllowsMultiple());
        assertEquals(1, entity.getPoints());
        assertNotNull(entity.getThemes());
        assertNotNull(entity.getMcAnswers());
        assertNotNull(entity.getGapFields());
    }

    @Test
    void constructorShouldSetProvidedFields() {
        QuestionEntity entity = new QuestionEntity(11, QuestionType.MC, "What is SQL?");

        assertEquals(11, entity.getQuestionSetId());
        assertEquals(QuestionType.MC, entity.getQuestionType());
        assertEquals("What is SQL?", entity.getStartText());
    }

    @Test
    void toStringShouldContainCoreFields() {
        QuestionEntity entity = new QuestionEntity(3, QuestionType.TF, "Statement");
        entity.setId(42);

        String value = entity.toString();

        assertTrue(value.contains("id=42"));
        assertTrue(value.contains("questionSetId=3"));
        assertTrue(value.contains("questionType=TF"));
    }
}
