package com.gvi.project.models.objects;

import com.gvi.project.components.AnimationComponent;
import com.gvi.project.GamePanel;

import java.util.List;


public class OBJ_Key extends AnimatedObject {
	public OBJ_Key(KeyType typ) {
		super("/sprites/tilemaps/damp-dungeons/Animations/Dungeon_ObjectsDungeon", typ.getSpriteGroupID());
		id = typ.getSpriteGroupID();
		name = typ.getName();
		canInteract = true;
		interactHint = "[F] Pick up %s Key".formatted(name);
		collision = true;
		setUpAnimationComponent();
	}

	@Override
	public void onConfirm(GamePanel gp, int objIndex) {
		gp.playSE(1);

		gp.player.addItem(id, 1);

		gp.obj.remove(objIndex);
		gp.ui.openMessage("You got a %s key!".formatted(name));
	}

	@Override
	public void setUpAnimationComponent(){
		AnimationComponent animComp = (AnimationComponent) this.components.get("Animation");
		animComp.setLooping(true);
		animComp.setCycleOrder(List.of(0,1,2,3,0));
		animComp.cycleLength = 0.6;
		animComp.delayBetweenCycles = 1;
		animComp.triggerLoop();
	}
}
