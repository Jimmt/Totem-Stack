package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Cloud extends Image {

	public Cloud(String path) {
		super(new Texture(Gdx.files.internal(path)));
	}
}
