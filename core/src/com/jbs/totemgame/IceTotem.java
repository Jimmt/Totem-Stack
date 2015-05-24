package com.jbs.totemgame;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IceTotem extends Totem {
	Stage particleStage;
	Image noAura;
	boolean unfreeze;
	boolean freezeTotem;

	public IceTotem(float x, float y, float scale, World world, Stage particleStage) {
		super(x, y, scale, world);

		this.particleStage = particleStage;

		Image img = Icons.getImage("totem/ice/ice.png");
		setDrawable(img.getDrawable());
		width = img.getWidth() * Constants.SCALE * scale;
		height = img.getHeight() * Constants.SCALE * scale;
		setSize(width, height);

	}

	public void unfreeze() {
		unfreeze = true;
	}

	public void removeAura() {
		noAura = Icons.getImage("totem/ice/icenormal.png");
		setDrawable(noAura.getDrawable());
		width = noAura.getWidth() * Constants.SCALE * scale;
		height = noAura.getHeight() * Constants.SCALE * scale;
		setSize(width, height);
	}

	@Override
	public void act(float delta) {
		super.act(delta);


		if(freezeTotem){
			body.setType(BodyType.StaticBody);
		}
		
		if (unfreeze && body.getType() != BodyType.DynamicBody) {
			body.setType(BodyType.DynamicBody);
		}

		if (flag != null) {
			flag.animatedSprite.setPosition(getX() + getWidth() - 45f * Constants.SCALE, getY()
					+ getHeight() - 70f * Constants.SCALE);
		}

		parachute.setPosition(getX(), getY() + getHeight() - 20f * Constants.SCALE);

	}

}
