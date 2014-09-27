package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Totem extends Box2DActor {

	public Totem(String path, float y, World world) {
		super(path, world);

		bodyDef.type = BodyType.DynamicBody;
		fixtureDef.restitution = 0.0f;
		createBody();
		body.setUserData(this);
		body.setTransform(Constants.SCLWIDTH / 2, y, 0);

	}

}
