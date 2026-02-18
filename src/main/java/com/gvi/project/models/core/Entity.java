package com.gvi.project.models.core;

import javafx.scene.image.Image;

import java.awt.*;

public abstract class Entity {
	public int worldX, worldY;
	public int speed;

	public Image up1, down1, left1, right1, up2, down2, left2, right2;
	public String direction;

	public int spriteCounter = 0;
	public int spriteNumber = 1;

	public Rectangle collisionBox;
	public int collisionBoxDefaultX, collisionBoxDefaultY;
	public boolean collisionActive = false;
}

