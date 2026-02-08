package com.gvi.project.models.objects;

import com.gvi.project.helper.ImageHelper;

import java.io.IOException;

public class OBJ_Chest extends SuperObject {
	public OBJ_Chest(){
		name = "Chest";
		try {
			image = ImageHelper.getImage("/sprites/objects/chest.png");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
