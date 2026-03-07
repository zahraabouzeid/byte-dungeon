package com.gvi.project.manager;

import com.gvi.project.models.sprite_sheets.Sprite;
import com.gvi.project.models.sprite_sheets.SpriteSheet;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class SpriteManager {

	public Map<String, Sprite> sprites = new HashMap<>();

	public SpriteManager() {}

	public void registerSprite(String key, Sprite sprite) {
		if (!sprites.containsKey(key)) {
			sprites.put(key, sprite);
		}
	}

	public Sprite getStoredSprite(String key) {
		return sprites.get(key);
	}

	/**
	 * @param spriteSheetPath Pfad ohne fileending wie .json oder .png
	 * @apiNote überprüft ob sprite in spritesMap geladen wurden wenn nicht wird es manuell geladen
	 */

	public Image getSpriteImage(String spriteSheetPath, String spriteGroup, String spriteId) {
		String[] path = spriteSheetPath.split("/");

		String key = "%s:%s:%s".formatted(path[path.length-1], spriteGroup, spriteId);

		if (sprites.containsKey("0:" + key) || sprites.containsKey("1:" + key)) {
			return sprites.get("0:" + key).image;
		};

		SpriteSheet sheet = new SpriteSheet(spriteSheetPath);
		return sheet.getSpriteImage(spriteGroup, spriteId);
	}

	public void reset(){
		if (sprites != null){
			sprites.clear();
		}
	}
}
