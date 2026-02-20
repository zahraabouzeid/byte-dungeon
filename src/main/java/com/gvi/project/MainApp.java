package com.gvi.project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApp extends Application {

	private static ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		// Spring Boot Context starten
		springContext = new SpringApplicationBuilder(MainApp.class)
				.headless(false)
				.run();
	}

	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("GVI Project");
		GamePanel gp = new GamePanel();

		BorderPane root = new BorderPane();
		root.setCenter(gp.canvas);

		mainStage.setScene(new Scene(root));
		mainStage.setResizable(false);

		mainStage.setOnShown(e -> centerStage(mainStage));
		mainStage.show();
	}

	@Override
	public void stop() {
		springContext.close();
		Platform.exit();
	}

	private void centerStage(Stage stage) {
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((bounds.getHeight() - stage.getHeight()) / 2);
	}

	/**
	 * Zugriff auf Spring Beans von überall im Code
	 */
	public static <T> T getBean(Class<T> beanClass) {
		return springContext.getBean(beanClass);
	}
}
