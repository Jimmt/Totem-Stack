package com.jbs.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Background {
	Ground ground;
	Image rocks, mountains, sky;

	public Background(Stage stage, World world) {
		
		sky = new Image(Icons.getTex("bg/skygradient.png"));
		sky.setScale(Constants.SCALE);
		stage.addActor(sky);
		sky.setHeight(Zone.RAIN.getY() / Constants.SCALE);
		sky.setWidth(Constants.WIDTH);	
		
		mountains = new Image(Icons.getTex("bg/mountain.png"));
		mountains.setScale(Constants.SCALE);
		mountains.setWidth(Constants.WIDTH);
		
		stage.addActor(mountains);
		rocks = new Image(Icons.getTex("bg/bigrock.png"));
		rocks.setScale(Constants.SCALE);
		rocks.setY(230f * Constants.SCALE);
		rocks.setWidth(Constants.WIDTH);
		mountains.setY(rocks.getY() + 256f * Constants.SCALE);
		stage.addActor(rocks);

		ground = new Ground("bg/ground.png", world);
		
		ground.bodyDef.type = BodyType.StaticBody;
		ground.body.setTransform(ground.getWidth() / 2, ground.boxHeight / 2, 0);
		stage.addActor(ground);
		ground.setWidth(Constants.SCLWIDTH);

	}
}
