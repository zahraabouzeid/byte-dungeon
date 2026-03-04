package com.gvi.project.models.game_maps;

import com.gvi.project.GamePanel;
import com.gvi.project.helper.ConfigHelper;
import com.gvi.project.models.game_maps.config.GameMapConfig;
import com.gvi.project.models.game_maps.config.GameMapLayerConfig;
import com.gvi.project.models.game_maps.config.GameMapSpriteConfig;
import com.gvi.project.models.game_maps.config.GameMapSpriteSheetConfig;
import com.gvi.project.models.sprite_sheets.Sprite;
import com.gvi.project.models.sprite_sheets.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMapLoader {
	private GamePanel gp;

	Map<String, SpriteSheet> spriteSheets;

	public GameMapLoader(GamePanel gp) {
		this.gp = gp;
		this.spriteSheets = new HashMap<>();
	}

	public GameMap loadMap(String filename) {
		gp.spriteManager.reset();
		GameMapConfig config;

		config = ConfigHelper.getConfig(GameMapConfig.class, "/maps/%s".formatted(filename));

		assert config != null;

		return buildMap(config);
	}

	private GameMap buildMap (GameMapConfig config) {
		GameMap map = new GameMap(
				config.id,
				config.name,
				config.width,
				config.height
		);

		addGameMapLayers(map, config);

		return map;
	}


	private void addGameMapLayers(GameMap map, GameMapConfig config) {
		for (GameMapLayerConfig layerConfig : config.layers){
			GameMapLayer layer = new GameMapLayer(config.width, config.height);

			parseSpriteInformation(layer, layerConfig, config.width, config.height);

			map.addLayer(layerConfig.renderId, layer);
		}
	}

	private void parseSpriteInformation(GameMapLayer layer, GameMapLayerConfig layerConfig, int width, int height){
		List<String> usedSpriteKeys = registerUsedSprites(layerConfig.usedSpriteSheets);

		for (int y = 0; y < width; y++){
			for (int x = 0; x < height; x++){
				int spriteId = layerConfig.spriteLayout[y][x];

				if (spriteId == 999) {
					layer.layout[y][x] = "empty";
					continue;
				};

				layer.layout[y][x] = usedSpriteKeys.get(spriteId);
			}
		}
	}

	private List<String> registerUsedSprites(GameMapSpriteSheetConfig[] sheetConfigs) {
		List<String> spriteKeys = new ArrayList<>();
		registerBasicSprites();

		for (GameMapSpriteSheetConfig mapSheetConfig : sheetConfigs) {
			//Überprüfen ob SpriteSheet geladen wurde. Wenn nicht lade SpriteSheet und speicher es in spriteSheets Map
			if (!this.spriteSheets.containsKey(mapSheetConfig.fileName)){
				spriteSheets.put(mapSheetConfig.fileName, new SpriteSheet(mapSheetConfig.filePath + mapSheetConfig.fileName));
			}

			SpriteSheet spriteSheet = spriteSheets.get(mapSheetConfig.fileName);

			//Iteriere über Spritegruppen, isoliere Teilbilder und registriere Sprites in SpriteManager
			for (GameMapSpriteConfig usedSprite : mapSheetConfig.usedSprites){

				Sprite sprite = new Sprite();
				sprite.image = spriteSheet.getSpriteImage(usedSprite.spriteGroup, usedSprite.spriteId);
				sprite.hasCollision = usedSprite.hasCollision;

				String key = "%s:%s:%s:%s".formatted(usedSprite.hasCollision ? "1": "0",mapSheetConfig.fileName, usedSprite.spriteGroup, usedSprite.spriteId);
				this.gp.spriteManager.registerSprite(key, sprite);
				spriteKeys.add(key);
			}
		}


		return spriteKeys;
	}

	private void registerBasicSprites(){
		Sprite emptySprite = new Sprite();
		emptySprite.hasCollision = false;
		emptySprite.image = new WritableImage(gp.generalSettings.tileSize, gp.generalSettings.tileSize);

		this.gp.spriteManager.registerSprite("empty", emptySprite);

	}
}
