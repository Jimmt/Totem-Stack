package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public class SoundManager {
	ObjectMap<String, Sound> sounds;
	ObjectMap<String, Music> musics;

	private boolean playEffects = true, playMusic = true, masterPlay = true;
	private boolean gameScreen, menuScreen = true;

	public SoundManager() {
		sounds = new ObjectMap<String, Sound>();
		musics = new ObjectMap<String, Music>();
	}

	public void update(AbstractScreen screen) {
		if (screen instanceof GameScreen) {
			gameScreen = true;
			menuScreen = false;
		}
		if (screen instanceof MenuScreen) {
			gameScreen = false;
			menuScreen = true;
		}
	}

	public void play(String name) {
		if (playEffects && masterPlay)
			((Sound) sounds.get(name)).play(0.5f);
	}

	public void play(String name, float volume) {
		if (playEffects && masterPlay)
			((Sound) sounds.get(name)).play(volume);
	}

	public void loopMusic(String name, float volume) {
		if (playMusic && masterPlay) {
			((Music) musics.get(name)).setLooping(true);
			((Music) musics.get(name)).setVolume(volume);
			((Music) musics.get(name)).play();
		}
	}

	public void loop(String name) {
		if (playMusic && masterPlay)
			((Sound) sounds.get(name)).loop();
	}

	public void loadSound(String name, FileHandle file) {
		Sound sound = Gdx.audio.newSound(file);
		sounds.put(name, sound);
	}

	public void loadMusic(String name, FileHandle file) {
		Music music = Gdx.audio.newMusic(file);
		musics.put(name, music);
	}

	public void setPlayEffects(boolean playEffects) {
		this.playEffects = playEffects;
	}

	public void setPlayMusic(boolean playMusic) {
		this.playMusic = playMusic;

		if (playMusic == false) {
			((Music) musics.get("game")).pause();
			((Music) musics.get("menu")).pause();
		} else {
			if (menuScreen) {
				((Music) musics.get("menu")).play();
			}
			if (gameScreen) {
				((Music) musics.get("game")).play();
			}
		}
	}
	
	public boolean getPlay(){
		return playMusic;
	}
	
	public boolean getMasterPlay(){
		return masterPlay;
	}

	public void setMasterPlay(boolean masterPlay) {
		this.masterPlay = masterPlay;
	}
	
	public void stopMusic(){
		Entries<String, Music> entries = musics.entries();
		while (entries.hasNext()) {
			entries.next().value.pause();
		}
	}
	
}
