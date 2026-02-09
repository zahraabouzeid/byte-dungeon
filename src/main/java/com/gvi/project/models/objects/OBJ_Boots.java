package com.gvi.project.models.objects;

import com.gvi.project.helper.ImageHelper;

import java.io.IOException;

public class OBJ_Boots extends SuperObject{
	public OBJ_Boots(){
		name = "Boots";
		try {
			image = ImageHelper.getImage("/sprites/objects/boots.png");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
