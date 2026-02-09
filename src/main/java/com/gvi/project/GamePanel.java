package com.gvi.project;

import com.gvi.project.models.entities.Player;
import com.gvi.project.models.objects.SuperObject;
import com.gvi.project.models.tiles.TileManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GamePanel {
	final int originalTileSize = 16;  // 16x16 px
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48x48 px
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;

	public final int screenWidth = maxScreenCol * tileSize;
	public final int screenHeight = maxScreenRow * tileSize;

	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	int FPS = 60;

	double drawInterval = 1000000000.0 / FPS;

	TileManager tileManager = new TileManager(this);
	KeyHandler keyHandler = new KeyHandler();
	Sound music = new Sound();
	Sound se = new Sound();

//	final GeneralSettings gs= new GeneralSettings();
//	final GameScreen gameScreen = new GameScreen(this.gs.getScreenDimensions().width, gs.getScreenDimensions().height);
	public final Canvas canvas = new Canvas(screenWidth, screenHeight);
	public final GraphicsContext gc = canvas.getGraphicsContext2D();
	public final GameLoop gameLoop = new GameLoop(this);
	public final AssetSetter assetSetter = new AssetSetter(this);
	public final Player player = new Player(this, keyHandler);
	public final UI ui = new UI(this);
	public final CollisionChecker cChecker = new CollisionChecker(this);
	public final SuperObject[] obj = new SuperObject[10];

	public GamePanel(){
		keyHandler.setupKeyListeners(canvas);
		gc.setImageSmoothing(false);
		setupGame();
		startGameLoop();
	}

	public void setupGame() {
		assetSetter.setObject();
		playMusic(0);
	}

	public void startGameLoop() {
		gameLoop.start();
	}

	public void stopGameLoop() {
		gameLoop.stop();
	}

	public void playMusic(int i){
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic(){
		music.stop();
	}

	public void playSE(int i){
		se.setFile(i);
		se.play();
	}
}
