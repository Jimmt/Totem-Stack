package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Totem extends Box2DActor {
	protected Flag flag;
	private Rectangle rect;
	private int random;
	protected Image parachute;
	protected Image freeze;
	boolean isFrozen = false, enableParachute = true;

	public Totem(float x, float y, float scale, World world) {
		super("totem/0" + MathUtils.random(3) + ".png", scale, world);

		
		random = MathUtils.random(3);

		parachute = new Image(new Texture(Gdx.files.internal("totem/parachute.png")));
		parachute.setScale(Constants.SCALE);
		parachute.setSize(parachute.getWidth() * scale, parachute.getHeight() * scale);
		freeze = Icons.returnImage("totem/ice/freeze.png");

		bodyDef.type = BodyType.DynamicBody;
		bodyDef.angularDamping = 1.0f;

		fixtureDef.restitution = 0.0f;
		fixtureDef.density = 5.0f;
		fixtureDef.friction = 1.0f;

		//shape = new PolygonShape();
		//shape.setAsBox(((getWidth()-2)*Constants.SCALE) / 2, ((getHeight()-2)*Constants.SCALE) / 2);
		//fixtureDef.shape = shape;
		
		createBody();
		body.setUserData(this);
		body.setTransform(x, y, 0);
		setY(y);
		setX(x);

		rect = new Rectangle(x, y, getWidth(), getHeight());
		
		parachute.setPosition(getX() - 25f * Constants.SCALE, getY() + getHeight() -3f
				* Constants.SCALE);
		
		freeze.setPosition(getX() + getWidth() / 2 - freeze.getWidth() / 2 * Constants.SCALE,
				getY());
	}

	public void freeze() {
		isFrozen = true;
		freeze.setPosition(getX() + getWidth() / 2 - freeze.getWidth() / 2 * Constants.SCALE,
				getY());
		
		
	}

	public Flag getFlag() {
		return flag;
	}

	public void removeParachute() {
		enableParachute = false;
	}

	public void createFlag() {
		flag = new Flag(getX(), getY());
		getStage().addActor(flag);
	}

	public Rectangle getRectangle() {
		return rect;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (isFrozen) {
			freeze.draw(batch, parentAlpha);
		}

		
		if (enableParachute) {
			parachute.draw(batch, parentAlpha);
		}

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(isFrozen && body.getType() != BodyType.StaticBody){
			body.setType(BodyType.StaticBody);
		}

		rect.set(getX(), getY(), getWidth(), getHeight());

		freeze.setPosition(getX() + getWidth() / 2 - freeze.getWidth() / 2 * Constants.SCALE,
				getY());

		if (flag != null) {
			flag.animatedSprite.setPosition(getX() + getWidth() - 20f * Constants.SCALE, getY()
					+ getHeight() - 52f * Constants.SCALE);
		}

		parachute.setPosition(getX() - 0.15f * parachute.getWidth() * Constants.SCALE, getY() + getHeight() -3f
				* Constants.SCALE);
		
		parachute.setRotation(getRotation());
		
		parachute.setOrigin(getWidth() * Constants.SCALE / 2, -MathUtils.sinDeg(getRotation()));

		setOrigin(width / 2, height / 2);

		setRotation(body.getTransform().getRotation() * MathUtils.radDeg);
	}

}
