package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Cloud extends Image {
	Rectangle hitbox;
	float influence;

	public Cloud(float height, float influence) {
		super(Icons.getTex("bg/Clouds0" + MathUtils.random(3) + ".png"));

		this.influence = influence;

		setScale(Constants.SCALE);

		setX(Constants.SCLWIDTH / 2 - getWidth() / 2 * Constants.SCALE + MathUtils.random(-0.5f, 0.5f));
		setY(height);

		hitbox = new Rectangle();
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		hitbox.set(getX(), getY(), getWidth() * Constants.SCALE, getHeight() * Constants.SCALE);

		setX(getX() + influence);
	}
}
