package com.izipoker.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.izipoker.client.IZIPokerClient;

public class DesktopClient {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		config.width = 300;
		config.height = 400;
		config.resizable = false;
		new LwjglApplication(IZIPokerClient.getInstance(), config);
	}
}
