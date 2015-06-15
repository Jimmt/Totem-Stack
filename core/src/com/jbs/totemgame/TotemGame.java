package com.jbs.totemgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TotemGame extends Game {
	public static SoundManager soundManager;
	public static IGoogleServices services;
	public static boolean DEBUG = true;
	public static int deaths = 0;

	String[] paths = {};

	public TotemGame(IGoogleServices services) {
		this.services = services;
	}

	@Override
	public void create() {
		soundManager = new SoundManager();

		soundManager.loadSound("hitground", Gdx.files.internal("sfx/game/hitground.mp3"));
		soundManager.loadSound("lose", Gdx.files.internal("sfx/game/lose.wav"));
		soundManager.loadSound("normalland", Gdx.files.internal("sfx/game/normalland.wav"));
		soundManager.loadSound("perfectland", Gdx.files.internal("sfx/game/perfectland.wav"));
		soundManager.loadSound("rain", Gdx.files.internal("sfx/game/rain.wav"));
		soundManager.loadSound("thunder00", Gdx.files.internal("sfx/game/thunder00.wav"));
		soundManager.loadSound("thunder01", Gdx.files.internal("sfx/game/thunder01.wav"));
		soundManager.loadSound("thunder02", Gdx.files.internal("sfx/game/thunder02.wav"));
		soundManager.loadSound("wind", Gdx.files.internal("sfx/game/wind.wav"));
		soundManager.loadSound("button", Gdx.files.internal("sfx/ui/button.wav"));
		soundManager.loadSound("buy", Gdx.files.internal("sfx/ui/buy.wav"));
		soundManager.loadSound("check", Gdx.files.internal("sfx/ui/check.wav"));
		soundManager.loadSound("welcomeback", Gdx.files.internal("sfx/ui/welcomeback.wav"));
		soundManager.loadMusic("menu", Gdx.files.internal("sfx/game/menumusic.mp3"));
		soundManager.loadMusic("game", Gdx.files.internal("sfx/game/gamemusic.mp3"));

		GamePrefs.initialize();
		Icons.loadIcons();
		
		
		
		

		DEBUG = false;

		if (!DEBUG) {
			setScreen(new SplashScreen(this));
		} else {
			setScreen(new MenuScreen(this));
		}
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
		soundManager.update((AbstractScreen) getScreen());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
// super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

}
