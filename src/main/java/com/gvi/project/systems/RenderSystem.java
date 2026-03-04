package com.gvi.project.systems;

import com.gvi.project.GamePanel;
import com.gvi.project.models.core.Renderable;
import com.gvi.project.models.game_maps.GameMapLayer;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RenderSystem {
	private GamePanel gp;

	public RenderSystem(GamePanel gp) {
		this.gp = gp;
	}

	public void render() {
		renderLayer(gp.currentMap.getLayer("FLOOR"));

		List<Renderable> dynamic = new ArrayList<>();
		dynamic.addAll(gp.entityList);
		dynamic.add(gp.player);

		dynamic.sort(Comparator.comparingInt(Renderable::getY));

		for (Renderable r : dynamic) {
			r.render(gp.gc);
		}

		renderLayer(gp.currentMap.getLayer("CEILING"));

		gp.ui.minimap.draw(gp.gc);
		gp.ui.draw(gp.gc);
	}

	private void renderLayer(GameMapLayer layer) {
		int tileSize = gp.generalSettings.tileSize;

		for(int y = 0; y < layer.layout.length; y++){
			for(int x = 0; x < layer.layout[y].length; x++){
				gp.gc.drawImage(gp.spriteManager.getSprite(layer.layout[y][x]).image, x * tileSize, y * tileSize, tileSize, tileSize);
			}
		}
	}
}
