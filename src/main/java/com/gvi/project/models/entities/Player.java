package com.gvi.project.models.entities;

import com.gvi.project.GamePanel;
import com.gvi.project.KeyHandler;
import com.gvi.project.helper.ImageHelper;
import com.gvi.project.helper.TimeoutHelper;
import com.gvi.project.models.core.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;


public class Player extends Entity {
	private GamePanel gp;
	private KeyHandler keyH;

	public final int screenX;
	public final int screenY;

	public int playerKeys = 0;


	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		this.collisionBox = new Rectangle(8, 16, 32, 32);

		collisionBoxDefaultX = collisionBox.x;
		collisionBoxDefaultY = collisionBox.y;

		setDefaultValues();
		getPlayerSprites();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 23;
		speed = 4;
		direction = "down";
	}

	public void getPlayerSprites() {
		try {
			up1 = ImageHelper.getImage( "/sprites/entities/player/player_up_1.png");
			up2 = ImageHelper.getImage("/sprites/entities/player/player_up_2.png");
			down1 = ImageHelper.getImage("/sprites/entities/player/player_down_1.png");
			down2 = ImageHelper.getImage("/sprites/entities/player/player_down_2.png");
			left1 = ImageHelper.getImage("/sprites/entities/player/player_left_1.png");
			left2 = ImageHelper.getImage("/sprites/entities/player/player_left_2.png");
			right1 = ImageHelper.getImage("/sprites/entities/player/player_right_1.png");
			right2 = ImageHelper.getImage("/sprites/entities/player/player_right_2.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
			if (keyH.upPressed) {
				direction = "up";
			}

			if (keyH.downPressed) {
				direction = "down";
			}

			if (keyH.leftPressed) {
				direction = "left";
			}

			if (keyH.rightPressed) {
				direction = "right";
			}

			// CHECK TILE COLLISION
			collisionActive = false;
			gp.cChecker.checkCollision(this);

			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if (!collisionActive) {
				switch (direction) {
					case "up":
						this.worldY -= this.speed;
						break;
					case "down":
						this.worldY += this.speed;
						break;
					case "left":
						this.worldX -= this.speed;
						break;
					case "right":
						this.worldX += this.speed;
						break;
				}
			}

			spriteCounter++;

			if (spriteCounter > 12) {
				if (spriteNumber == 1) {
					spriteNumber = 2;
				} else if (spriteNumber == 2) {
					spriteNumber = 1;
				}
				spriteCounter = 0;
			}
		}
	}

	public void pickUpObject (int i) {
		if (i != 999){
			String objName = gp.obj[i].name;
			switch (objName) {
				case "Key":
					gp.playSE(1);
					playerKeys++;
					gp.obj[i] = null;

					gp.ui.openMessage("You got a key!");

					System.out.println("Keys: " + playerKeys);
					break;
				case "Door":
					if(playerKeys > 0){
						gp.obj[i] = null;
						gp.playSE(3);
						playerKeys--;
						gp.ui.openMessage("You opened the door!");
					} else {
						gp.ui.openMessage("You need a key!");
					}
					break;
				case "Boots":
					gp.playSE(2);
					speed = 8;
					gp.obj[i] = null;
					gp.ui.openMessage("SPEED UP!");
					TimeoutHelper.setTimeout(() -> {
						speed = 4;
					}, 10000);
					break;
				case "Chest":
					gp.ui.gameFinished = true;
					gp.stopMusic();
					gp.playSE(4);
					break;
			}
		}
	}

	public void draw(GraphicsContext gc) {
		Image image = null;

		switch (direction) {
			case "up":

				if (spriteNumber == 1) {
					image = up1;
				}
				if (spriteNumber == 2) {
					image = up2;
				}
				break;
			case "down":
				if (spriteNumber == 1) {
					image = down1;
				}
				if (spriteNumber == 2) {
					image = down2;
				}
				break;
			case "left":
				if (spriteNumber == 1) {
					image = left1;
				}
				if (spriteNumber == 2) {
					image = left2;
				}
				break;
			case "right":
				if (spriteNumber == 1) {
					image = right1;
				}
				if (spriteNumber == 2) {
					image = right2;
				}
				break;
		}

		gc.drawImage(image, this.screenX, this.screenY, gp.tileSize, gp.tileSize);
	}
}
