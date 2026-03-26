package com.gvi.project.helper;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColorHelperTest {

    @Test
    void getAverageColorShouldIgnoreTransparentPixels() {
        WritableImage image = new WritableImage(2, 2);
        PixelWriter writer = image.getPixelWriter();

        writer.setColor(0, 0, Color.RED);
        writer.setColor(1, 0, Color.RED);
        writer.setColor(0, 1, Color.TRANSPARENT);
        writer.setColor(1, 1, Color.TRANSPARENT);

        Color average = ColorHelper.getAverageColor(image);

        assertEquals(1.0, average.getRed(), 0.0001);
        assertEquals(0.0, average.getGreen(), 0.0001);
        assertEquals(0.0, average.getBlue(), 0.0001);
        assertEquals(1.0, average.getOpacity(), 0.0001);
    }

    @Test
    void getMostCommonColorShouldReturnDominantColorBucket() {
        WritableImage image = new WritableImage(4, 4);
        PixelWriter writer = image.getPixelWriter();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                writer.setColor(x, y, Color.rgb(240, 10, 10));
            }
        }

        writer.setColor(0, 0, Color.rgb(10, 10, 240));
        writer.setColor(1, 1, Color.rgb(10, 10, 240));

        Color dominant = ColorHelper.getMostCommonColor(image);

        assertTrue(dominant.getRed() > dominant.getBlue());
    }
}
