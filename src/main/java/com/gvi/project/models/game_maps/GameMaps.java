package com.gvi.project.models.game_maps;

public enum GameMaps {
	MAP_1("map_1.json"),
	MAP_2("map_2.json"),
	MAP_3("map_3.json"),
	TEST_MAP("mock_map_01.json");

	private String configFileName;

	GameMaps(String configFileName){
		this.configFileName = configFileName;
	}

	public String getConfigFileName(){
		return configFileName;
	}
}
