package com.gvi.project;

import com.gvi.project.models.objects.OBJ_Key;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;

public class UI {
	GamePanel gp;
	Font arial_40, arial_80B;
	Image keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;

	double playtime;
	DecimalFormat df = new DecimalFormat("#.00");

	public UI(GamePanel gp) {
		this.gp = gp;
		this.arial_40 = Font.font("Arial", 40);
		this.arial_80B = Font.font("Arial", FontWeight.BOLD, 80);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}

	public void openMessage(String message) {
		this.message = message;
		messageOn = true;
	}

	public void closeMessage() {
		messageOn = false;
	}

	public void draw(GraphicsContext gc) {

		if (gameFinished) {
			String text;
			double textLength;
			double x;
			double y;

			gc.setFont(arial_40);
			gc.setFill(Color.WHITE);

			text = "You found the Treasure!";
			textLength = getTextWidth(text, arial_40);
			x = gp.screenWidth / 2.0 - textLength / 2.0;
			y = gp.screenHeight / 2.0 - (gp.tileSize * 3);
			gc.fillText(text, x, y);

			text = "Your time is: " + df.format(playtime) + "!";
			textLength = getTextWidth(text, arial_40);
			x = gp.screenWidth / 2.0 - textLength / 2.0;
			y = gp.screenHeight / 2.0 + (gp.tileSize * 4);
			gc.fillText(text, x, y);

			gc.setFont(arial_80B);
			gc.setFill(Color.YELLOW);

			text = "Congratulations!";
			textLength = getTextWidth(text, arial_80B);
			x = gp.screenWidth / 2.0 - textLength / 2.0;
			y = gp.screenHeight / 2.0 + (gp.tileSize * 2);
			gc.fillText(text, x, y);

			gp.stopGameLoop();

		} else {
			gc.setFont(arial_40);
			gc.setFill(Color.WHITE);

			gc.drawImage(
				keyImage,
				gp.tileSize / 2.0,
				gp.tileSize / 2.0,
				gp.tileSize,
				gp.tileSize
			);

			gc.fillText("x " + gp.player.playerKeys, 74, 65);

			playtime += 1.0 / 60.0;
			gc.fillText("Time: " + df.format(playtime), gp.tileSize * 11, 65);

			if (messageOn) {
				gc.setFont(Font.font(gc.getFont().getFamily(), 30));
				gc.fillText(message, gp.tileSize / 2.0, gp.tileSize * 5);

				messageCounter++;
				if (messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
	}

	private double getTextWidth(String text, javafx.scene.text.Font font) {
		Text temp = new Text(text);
		temp.setFont(font);
		return temp.getLayoutBounds().getWidth();
	}

}
