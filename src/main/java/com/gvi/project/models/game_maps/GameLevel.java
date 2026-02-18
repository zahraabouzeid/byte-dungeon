package com.gvi.project.models.game_maps;

import java.util.ArrayList;

public class GameLevel {
	public String id;
	public int columnCount, rowCount;
	public ArrayList<LevelLayer> levelLayers;
	public ArrayList<Object> objects;

	public GameLevel() {}
}
