package com.gvi.project.models.sprite_sheets;

import com.gvi.project.helper.ConfigHelper;
import com.gvi.project.models.sprite_sheets.config.SpriteConfig;
import com.gvi.project.models.sprite_sheets.config.SpriteSheetConfig;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteSheet {
	private SpriteSheetConfig config;
	private Image sheetImage;

	/**
	 *
	 * @param path Der Pfad zum laden der SheetConfig sowie das dazugehörige SpriteSheet
	 */
	public SpriteSheet(String path) {
		this.config = ConfigHelper.getConfig(SpriteSheetConfig.class, "%s.json".formatted(path));
		this.sheetImage = new Image("%s.png".formatted(path));
	}

	// Isoliert Teilbild vom Spritesheet Bild
	public Image getSpriteImage(String spriteGroupId, String spriteId) {

		SpriteConfig spriteConfig = config.spriteGroups.get(spriteGroupId).sprites.get(spriteId);

		PixelReader reader = sheetImage.getPixelReader();
		WritableImage subImage = new WritableImage(reader, spriteConfig.spriteX * config.spriteSize, spriteConfig.spriteY * config.spriteSize, spriteConfig.spriteWidth * config.spriteSize, spriteConfig.spriteHeight * config.spriteSize);

		return subImage;
	}

	public SpriteSheetConfig getConfig() {
		return config;
	}
}
