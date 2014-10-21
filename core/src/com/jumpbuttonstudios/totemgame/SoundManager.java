package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

public class SoundManager {
	ObjectMap<String, Sound> sounds;

	private boolean playEffects = true, playMusic = true;

	public SoundManager() {
		sounds = new ObjectMap<String, Sound>();
	}

	public void play(String name) {
		if (playEffects)
			((Sound) sounds.get(name)).play();
	}
	
	public void play(String name, float volume) {
		if (playEffects)
			((Sound) sounds.get(name)).play(volume);
	}
	
	public void loop(String name) {
		if (playEffects)
			((Sound) sounds.get(name)).loop();
	}

	public void loadSound(String name, FileHandle file) {
		Sound sound = Gdx.audio.newSound(file);
		sounds.put(name, sound);
	}

	public void setPlayEffects(boolean playEffects) {
		this.playEffects = playEffects;
	}

	public void setPlayMusic(boolean playMusic) {
		this.playMusic = playMusic;
	}
}
