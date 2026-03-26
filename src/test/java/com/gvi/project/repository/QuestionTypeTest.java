package com.gvi.project.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionTypeTest {

    @Test
    void displayNamesShouldMatchExpectedValues() {
        assertEquals("Multiple Choice", QuestionType.MC.getDisplayName());
        assertEquals("Wahr/Falsch", QuestionType.TF.getDisplayName());
        assertEquals("Lückentext", QuestionType.GAP.getDisplayName());
    }
}
