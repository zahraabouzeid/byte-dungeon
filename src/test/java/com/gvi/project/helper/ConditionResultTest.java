package com.gvi.project.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConditionResultTest {

    @Test
    void addStringMessageShouldIgnoreEmptyMessage() {
        ConditionResult result = new ConditionResult(false, "initial");

        result.addStringMessage("");

        assertEquals("initial", result.getMessage());
    }

    @Test
    void addStringMessageShouldPrefixBullet() {
        ConditionResult result = new ConditionResult(false, "initial");

        result.addStringMessage("missing key");

        assertEquals("initial\n- missing key", result.getMessage());
    }

    @Test
    void constructorShouldSetSuccessAndMessage() {
        ConditionResult result = new ConditionResult(true, "ok");

        assertTrue(result.success);
        assertEquals("ok", result.getMessage());
    }
}
