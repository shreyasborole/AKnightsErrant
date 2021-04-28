package com.mygdx.game.desktop;

import java.time.Instant;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.AKEGame;
import com.mygdx.game.utils.Debug;

public class DesktopLauncher {

	private static LwjglApplicationConfiguration getDefaultConfiguration(){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "A Knight's Errant";
		config.width = 1280;
		config.height = 800;
		config.resizable = false;
		config.vSyncEnabled = false; // Setting to false disables vertical sync
		config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
		config.backgroundFPS = 0; // Setting to 0 disables background fps throttling
		// config.fullscreen = true;
		return config;
	}

	private static LwjglApplication createApplication(LwjglApplicationConfiguration config){
		Debug.startTime = Instant.now();
		return new LwjglApplication(new AKEGame(), config);
	}
	public static void main (String[] arg) {
		createApplication(getDefaultConfiguration());
	}
}