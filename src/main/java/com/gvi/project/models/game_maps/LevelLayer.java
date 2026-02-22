package com.gvi.project.models.game_maps;

import com.gvi.project.models.tiles.GameLevelSpriteSheet;

import java.util.ArrayList;

public class LevelLayer {
	public int id;
	public ArrayList<GameLevelSpriteSheet> usedSpriteSheets;
	public ArrayList<ArrayList<Integer>> spriteLayout;

	public LevelLayer() {}
}
