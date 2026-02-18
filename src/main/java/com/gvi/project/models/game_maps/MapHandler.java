package com.gvi.project.models.game_maps;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MapHandler {
	private static ObjectMapper mapper = new ObjectMapper();
	private static GameLevel currentLevel;

	public static GameLevel initMap(String mapFilePath) {
		try {
			GameLevel loadedLevel = mapper.readValue(mapFilePath, GameLevel.class);
			currentLevel = loadedLevel;
			return loadedLevel;
		} catch (IOException e) {
			System.out.println("Error while parsing file: " + mapFilePath);
		}

		return currentLevel;
	}
}
