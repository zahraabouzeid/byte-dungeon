package com.gvi.project.models.objects;

import com.gvi.project.helper.ImageHelper;

import java.io.IOException;

public class OBJ_Key extends SuperObject {
	public OBJ_Key(){
		name = "Key";

		try {
			image = ImageHelper.getImage( "/sprites/objects/key.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
