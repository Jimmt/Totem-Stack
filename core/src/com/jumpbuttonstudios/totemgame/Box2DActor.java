package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Box2DActor extends Image {
	private World world;
	PolygonShape shape;
	BodyDef bodyDef;
	FixtureDef fixtureDef;
	public Body body;
	float width, height;

	public Box2DActor(String path, float width, float height, World world) {
		super(Icons.getTex(path));

		this.width = width;
		this.height = height;

		this.world = world;

		shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;

		setSize(width, height);
	}

	public Box2DActor(String path, World world) {
		super(Icons.getTex(path));

		this.width = getWidth() * Constants.SCALE;
		this.height = getHeight() * Constants.SCALE;

		this.world = world;

		shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;

		setSize(width, height);
	}

	public void createBody() {
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (body != null) {
			setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
		} else {
			System.out.println("body not initialized");
		}

	}
}
