package com.gvi.project.models.core;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
	int getY();
	void render(GraphicsContext gc);
}
