package com.gvi.project.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UIUtilsTest {

    @Test
    void textMetricsShouldReturnPositiveValues() {
        Font font = Font.font(16);

        double width = UIUtils.getTextWidth("SQL Dungeon", font);
        double height = UIUtils.getTextHeight("SQL Dungeon", font);

        assertTrue(width > 0);
        assertTrue(height > 0);
    }

    @Test
    void wrapTextShouldSplitLongTextIntoMultipleLines() {
        Font font = Font.font(14);

        List<String> lines = UIUtils.wrapText(
                "This is a long sentence that should wrap into more than one line",
                font,
                120
        );

        assertTrue(lines.size() > 1);
        assertFalse(lines.stream().anyMatch(String::isBlank));
    }

    @Test
    void drawMethodsShouldNotThrow() {
        Canvas canvas = new Canvas(400, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        assertDoesNotThrow(() -> UIUtils.drawPixelBox(gc, 20, 20, 150, 80));
        assertDoesNotThrow(() -> UIUtils.drawScreenBorder(gc, 400, 300));
    }
}
