package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GoldTotem extends Totem {
	Stage particleStage;
	Image aura, noAura;

	public GoldTotem(float x, float y, float scale, World world, Stage particleStage) {
		super(x, y, scale, world);

		this.particleStage = particleStage;

		aura = Icons.getImage("totem/special/specialshine.png");
		setDrawable(aura.getDrawable());
		width = aura.getWidth() * Constants.SCALE * scale;
		height = aura.getHeight() * Constants.SCALE * scale;
		setSize(width, height);

	}

	public void removeAura() {
		noAura = Icons.getImage("totem/special/special.png");
		setDrawable(noAura.getDrawable());
		width = noAura.getWidth() * Constants.SCALE * scale;
		height = noAura.getHeight() * Constants.SCALE * scale;
		setSize(width, height);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		

		if (flag != null) {
			flag.animatedSprite.setPosition(getX() + getWidth() - 20f * Constants.SCALE, getY()
					+ getHeight() - 74f * Constants.SCALE);
		}

		parachute.setPosition(getX(), getY() + getHeight() - 20f * Constants.SCALE);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}

}
