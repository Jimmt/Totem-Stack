package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GoldTotem extends Totem {

	public GoldTotem(float x, float y, World world) {
		super(x, y, world);

		Image img = Icons.getImage("totem/special/specialshine.png");
		setDrawable(img.getDrawable());
		width = img.getWidth() * Constants.SCALE;
		height = img.getHeight() * Constants.SCALE;
		setSize(width, height);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
			
		parachute.setPosition(getX(), getY() + getHeight());
	}

}
