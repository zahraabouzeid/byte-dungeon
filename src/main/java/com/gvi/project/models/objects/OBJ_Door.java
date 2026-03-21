package com.gvi.project.models.objects;

import com.gvi.project.Components.AnimationComponent;
import com.gvi.project.GamePanel;
import com.gvi.project.Sound;

public class OBJ_Door extends AnimatedObject {
	Sound sound = new Sound();
	public boolean isOpen = false;
	public boolean closable = false;

	public OBJ_Door() {
		super("/sprites/tilemaps/damp-dungeons/Animations/Dungeon_ObjectsDoorUp", "door");
		name = "door";
		id = name;
		interactHint = "[F] Unlock %s".formatted(name);
		spriteDirectionUp = true;
		collision = true;
		collisionBox.setWidth(2 * 16 * 3);
		canInteract = true;
		setUpAnimationComponent();
	}


	@Override
	public void onConfirm(GamePanel gp, int objIndex) {
		if (!canInteract) return;
//		if (player.playerItems.containsKey(KeyType.IRON.getName())) {
//			if(player.playerItems.get(KeyType.IRON.getName()) == 0) {}
			AnimationComponent animComp = (AnimationComponent) components.get("Animation");
			animComp.trigger();
			sound.setFile(4);
			sound.loop();
			sound.play();
			canInteract = false;
//		}

	}

	@Override
	public void setUpAnimationComponent(){
		AnimationComponent animComp = (AnimationComponent) this.components.get("Animation");
		animComp.cycleLength = 2;
		animComp.onFinished = () -> {
			this.collision = false;
			this.sound.stop();
		};
	}

	@Override
	public void onDestroy() {
		if(sound != null && sound.isRunning()) sound.stop();
	}
}
