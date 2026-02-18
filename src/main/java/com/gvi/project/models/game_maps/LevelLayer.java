package com.gvi.project.models.game_maps;

import com.gvi.project.models.tiles.Sprite;

import java.util.ArrayList;

public class LevelLayer {
	public int id;
	public ArrayList<Sprite> usedSprites;
	public ArrayList<ArrayList<Integer>> spriteLayout;

	public LevelLayer() {}
}
