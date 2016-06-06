package com.izipoker.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopClientLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		config.width = 800;
		config.height = 600;
		config.resizable = false;
		new LwjglApplication(IZIPokerAndroid.getInstance(), config);
	}
}
