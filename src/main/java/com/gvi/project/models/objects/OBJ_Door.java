package com.gvi.project.models.objects;

import com.gvi.project.helper.ImageHelper;

import java.io.IOException;

public class OBJ_Door extends SuperObject {
	public OBJ_Door(){
		name = "Door";
		try {
			image = ImageHelper.getImage("/sprites/objects/door.png");
		} catch (IOException e){
			e.printStackTrace();
		}

		collision = true;
	}
}
