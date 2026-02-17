package com.gvi.project.models.entities;

import com.gvi.project.GamePanel;
import com.gvi.project.KeyHandler;
import com.gvi.project.helper.ImageHelper;
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
	public int nearbyObjectIndex = -1;
	public int maxHealthHalf = 10;
	public int healthHalf = 10;
	public boolean isDead = false;
	public int score = 0;


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
		maxHealthHalf = 10;
		healthHalf = 10;
		isDead = false;
		score = 0;
	}

	public void takeHalfHeartDamage() {
		if (isDead) return;
		healthHalf = Math.max(0, healthHalf - 1);
		if (healthHalf == 0) {
			isDead = true;
		}
	}

	public void getPlayerSprites() {
		try {
			up1 = ImageHelper.getImage("/sprites/entities/player/player_up_1.png");
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

			// CHECK OBJECT COLLISION (only blocks movement, no pickup)
			int objIndex = gp.cChecker.checkObject(this, true);
			if (objIndex != 999 && gp.obj[objIndex] != null && gp.obj[objIndex].collision) {
				collisionActive = true;
			}

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

		nearbyObjectIndex = findNearbyObject();

		if (keyH.fPressed) {
			keyH.fPressed = false;
			if (nearbyObjectIndex != -1 && gp.obj[nearbyObjectIndex] != null) {
				gp.obj[nearbyObjectIndex].onInteract(this, gp, nearbyObjectIndex);
			}
		}
	}

	private int findNearbyObject() {
		int interactRange = gp.tileSize; 

		int playerCenterX = worldX + collisionBox.x + collisionBox.width / 2;
		int playerCenterY = worldY + collisionBox.y + collisionBox.height / 2;

		for (int i = 0; i < gp.obj.length; i++) {
			if (gp.obj[i] != null) {
				int objCenterX = gp.obj[i].worldX + gp.tileSize / 2;
				int objCenterY = gp.obj[i].worldY + gp.tileSize / 2;

				int dx = Math.abs(playerCenterX - objCenterX);
				int dy = Math.abs(playerCenterY - objCenterY);

				if (dx <= interactRange && dy <= interactRange) {
					return i;
				}
			}
		}
		return -1;
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
