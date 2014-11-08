package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePrefs {
	static Preferences prefs;

	public static void initialize() {
		prefs = Gdx.app.getPreferences("settings");
		if (!prefs.contains("bgMusic")) {
			prefs.putBoolean("bgMusic", true);
			prefs.putBoolean("soundEffects", true);
			prefs.putBoolean("tap", true);
			prefs.putInteger("bestScore", 0);
		}
	}

	public static void putBoolean(String name, boolean value) {
		prefs.putBoolean(name, value);
		prefs.flush();
	}
	
	public static void putInteger(String name, int value){
		prefs.putInteger(name, value);
		prefs.flush();
	}
}
