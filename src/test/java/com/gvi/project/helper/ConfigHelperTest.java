package com.gvi.project.helper;

import com.gvi.project.models.core.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigHelperTest {

    @Test
    void getConfigShouldReadJsonFromResources() {
        TestConfig config = ConfigHelper.getConfig(TestConfig.class, "/test-config.json");

        assertEquals("unit-test", config.name);
        assertEquals(7, config.level);
    }

    @Test
    void getConfigShouldThrowIfResourceMissing() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ConfigHelper.getConfig(TestConfig.class, "/missing-config.json"));

        assertTrue(exception.getMessage().contains("File not found in resources"));
    }

    public static class TestConfig extends Config {
        public String name;
        public int level;
    }
}
