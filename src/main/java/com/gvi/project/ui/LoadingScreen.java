package com.gvi.project.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static com.gvi.project.ui.UITheme.*;
import static com.gvi.project.ui.UIUtils.*;

public class LoadingScreen extends GameScreen {

    public static final int DURATION = 190;

    public LoadingScreen() {}

    public void draw(GraphicsContext gc, int frame) {
        gc.setFill(Color.rgb(10, 18, 12));
        gc.fillRect(0, 0, screenWidth, screenHeight);

        drawScreenBorder(gc, screenWidth, screenHeight);

        int dots = (frame / 15) % 4;
        String label = "LOADING" + ".".repeat(dots);

        gc.setFont(FONT_XL);
        double tw = getTextWidth(label, FONT_XL);
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillText(label, screenWidth / 2.0 - tw / 2.0 + 3, screenHeight / 2.0 + 3);
        gc.setFill(TEXT_GOLD);
        gc.fillText(label, screenWidth / 2.0 - tw / 2.0, screenHeight / 2.0);

        drawMessage(gc);
    }

    private void drawMessage(GraphicsContext gc) {
        double centerX = screenWidth / 2.0;
        double y = screenHeight / 2.0 + 450;

        String message = "Navigate through the dungeon, defeat puzzles with knowledge, gather keys, earn points and survive...";

        gc.setFont(FONT_MD);
        double textW = getTextWidth(message, FONT_MD);
        double x = centerX - textW / 2.0;

        gc.setFill(Color.rgb(0, 0, 0, 0.6));
        gc.fillText(message, x + 1, y + 1);
        gc.setFill(TEXT_WHITE);
        gc.fillText(message, x, y);
    }
}
