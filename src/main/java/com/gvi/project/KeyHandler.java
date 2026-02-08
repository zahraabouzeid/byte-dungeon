package com.gvi.project;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class KeyHandler {

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	// Attach key listeners to a node
	public void setupKeyListeners(Node node) {
		node.setOnKeyPressed(e -> {
			KeyCode code = e.getCode();
			if (code == KeyCode.W) upPressed = true;
			if (code == KeyCode.A) leftPressed = true;
			if (code == KeyCode.S) downPressed = true;
			if (code == KeyCode.D) rightPressed = true;
		});

		node.setOnKeyReleased(e -> {
			KeyCode code = e.getCode();
			if (code == KeyCode.W) upPressed = false;
			if (code == KeyCode.A) leftPressed = false;
			if (code == KeyCode.S) downPressed = false;
			if (code == KeyCode.D) rightPressed = false;
		});

		// Make sure the node has focus
		node.setFocusTraversable(true);
		node.requestFocus();
	}
}
