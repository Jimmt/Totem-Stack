package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GoldTotem extends Totem {
	Stage particleStage;

	public GoldTotem(float x, float y, World world, Stage particleStage) {
		super(x, y, world);
		
		this.particleStage = particleStage;

		Image img = Icons.getImage("totem/special/specialshine.png");
		setDrawable(img.getDrawable());
		width = img.getWidth() * Constants.SCALE;
		height = img.getHeight() * Constants.SCALE;
		setSize(width, height);

	
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (flag != null) {
			flag.animatedSprite.setPosition(getX() + getWidth() - 25f * Constants.SCALE, getY()
					+ getHeight() - 74f * Constants.SCALE);
		}

		parachute.setPosition(getX(), getY() + getHeight() - 20f * Constants.SCALE);
		
		
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		
		

	}

}
