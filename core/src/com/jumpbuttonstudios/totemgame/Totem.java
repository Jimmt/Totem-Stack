package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Totem extends Box2DActor {
	private Flag flag;
	private Rectangle rect;

	public Totem(float x, float y, World world) {
		super("totem/0" + MathUtils.random(3) + ".png", world);

		bodyDef.type = BodyType.DynamicBody;
		bodyDef.angularDamping = 1.0f;
		
		fixtureDef.restitution = 0.0f;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 1.0f;
		
		createBody();
		body.setUserData(this);
		body.setTransform(x, y, 0);
		setY(y);
		setX(x);

		rect = new Rectangle(x, y, getWidth(), getHeight());
	}
	
	public Flag getFlag(){
		return flag;
	}
	
	public void createFlag(){
		flag = new Flag(getX(), getY());
		getStage().addActor(flag);
	}
	
	public Rectangle getRectangle(){
		return rect;
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
		rect.set(getX(), getY(), getWidth(), getHeight());
		
		if(flag != null){
		flag.animatedSprite.setPosition(getX() + getWidth() - 20f * Constants.SCALE, getY() + getHeight() - 52f * Constants.SCALE);
		}
		
		setOrigin(width / 2, height / 2);
		
		setRotation(body.getTransform().getRotation() * MathUtils.radDeg);
	}

}
