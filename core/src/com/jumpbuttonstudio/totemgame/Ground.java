package com.jumpbuttonstudio.totemgame;



import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends Box2DActor {
	float boxHeight;

	public Ground(String path, World world) {
		super(path, 1, world);

		boxHeight = 93 * Constants.SCALE;

		shape.setAsBox(width, boxHeight / 2);

		fixtureDef = new FixtureDef();
		fixtureDef.friction = 1.0f;
		fixtureDef.shape = shape;
		createBody();
		body.setUserData(this);
	
	}

	@Override
	public void act(float delta) {
		setPosition(body.getPosition().x - width / 2, body.getPosition().y - boxHeight / 2);
	}

}
