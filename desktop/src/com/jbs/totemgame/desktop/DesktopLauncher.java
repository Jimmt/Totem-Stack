package com.jbs.totemgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jbs.totemgame.DesktopGoogleServices;
import com.jbs.totemgame.TotemGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 540;
		config.height = 960;

		new LwjglApplication(new TotemGame(new DesktopGoogleServices()), config);
	}
}   
