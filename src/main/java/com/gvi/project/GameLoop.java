package com.gvi.project;

import com.gvi.project.models.objects.SuperObject;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class GameLoop extends AnimationTimer {

	private final GamePanel gp;

	double delta = 0;
	long lastTime = System.nanoTime();
	int drawCount = 0;
	long timer = 0;

	public GameLoop(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void handle(long now) {

		long currentTime = System.nanoTime();

		delta += (currentTime - lastTime) / gp.drawInterval;
		timer += (currentTime - lastTime);
		lastTime = currentTime;

		if (delta >= 1) {
			updateEntities(delta);
			renderScreen();
			delta--;
			drawCount++;
		}

		if (timer >= 1_000_000_000) {
			System.out.println("FPS: " + drawCount);
			drawCount = 0;
			timer = 0;
		}
	}

	private void updateEntities(double deltaTime) {
		gp.player.update();
	}

	private void renderScreen() {
		gp.gc.clearRect(0, 0, gp.screenWidth, gp.screenHeight);
		gp.gc.setFill(Color.BLACK);
		gp.gc.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		gp.tileManager.draw(gp.gc);

		for (SuperObject obj : gp.obj) {
			if(obj != null) {
				obj.draw(gp);
			}
		}

		gp.player.draw(gp.gc);
		gp.ui.draw(gp.gc);
	}
}
