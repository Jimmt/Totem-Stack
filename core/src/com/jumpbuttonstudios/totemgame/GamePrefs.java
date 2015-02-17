package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePrefs {
	public static Preferences prefs;

	public static void initialize() {
		prefs = Gdx.app.getPreferences("settings");
		if (!prefs.contains("bgMusic")) {
			prefs.putBoolean("bgMusic", true);
			prefs.putBoolean("soundEffects", true);
			prefs.putBoolean("tap", true);
			prefs.putInteger("bestScore", 0);
			prefs.putInteger("retryUses", 0);
			prefs.putInteger("freezeUses", 0);
			prefs.putInteger("slowUses", 0);
			prefs.putInteger("windUses", 0);
			
			prefs.flush();
		}
		//testing
//		prefs.putInteger("coins", 0);
//		prefs.putInteger("retryUses", 99);
//		prefs.putInteger("freezeUses", 99);
//		prefs.putInteger("slowUses", 99);
//		prefs.putInteger("windUses", 99);
		
//		prefs.putInteger("retryUses", 0);
//		prefs.putInteger("freezeUses", 0);
//		prefs.putInteger("slowUses", 0);
//		prefs.putInteger("windUses", 0);
		
		prefs.flush();
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
