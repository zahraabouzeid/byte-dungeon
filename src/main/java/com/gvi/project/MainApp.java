package com.gvi.project;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("GVI Project");
		GamePanel gp = new GamePanel();

		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double desktopWidth = bounds.getWidth();
		double desktopHeight = bounds.getHeight();
		int gameWidth = gp.generalSettings.screenWidth;
		int gameHeight = gp.generalSettings.screenHeight;

		BorderPane root = new BorderPane();
		root.setCenter(gp.canvas);
		root.setPrefSize(gameWidth, gameHeight);

		// Skalierung mit gleichmäßigem Faktor, um das Aspekt-Verhältnis zu bewahren
		double scaleX = desktopWidth / gameWidth;
		double scaleY = desktopHeight / gameHeight;
		double scale = Math.min(scaleX, scaleY); // Verwende den kleineren Faktor
		root.setScaleX(scale);
		root.setScaleY(scale);

		Scene scene = new Scene(root, desktopWidth, desktopHeight);
		scene.setFill(javafx.scene.paint.Color.BLACK); // Schwarzer Hintergrund für die Balken
		mainStage.setScene(scene);
		mainStage.setResizable(false);
		mainStage.setFullScreen(true);
		mainStage.setFullScreenExitHint("Drücke ESC zum Verlassen des Vollbildmodus");
		mainStage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.valueOf("ESCAPE"));

		mainStage.show();
	}

	private void centerStage(Stage stage) {
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((bounds.getHeight() - stage.getHeight()) / 2);
	}
}
