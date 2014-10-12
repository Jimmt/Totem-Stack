package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IceTotem extends Totem {
	Stage particleStage;
	Image noAura;
	
	

	public IceTotem(float x, float y, World world, Stage particleStage) {
		super(x, y, world);

		this.particleStage = particleStage;

		
		
		Image img = Icons.getImage("totem/ice/ice.png");
		setDrawable(img.getDrawable());
		width = img.getWidth() * Constants.SCALE;
		height = img.getHeight() * Constants.SCALE;
		setSize(width, height);
	}

	

	public void removeAura() {
		noAura = Icons.getImage("totem/ice/icenormal.png");
		setDrawable(noAura.getDrawable());
		width = noAura.getWidth() * Constants.SCALE;
		height = noAura.getHeight() * Constants.SCALE;
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

}
