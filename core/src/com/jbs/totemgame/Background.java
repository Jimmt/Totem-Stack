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

	public Background(Stage stage, World world, boolean scaled) {

		float scale;

		if (scaled) {
			scale = Constants.SCALE;
		} else {
			scale = 1;
		}

		sky = new Image(Icons.getTex("bg/skygradient.png"));
		sky.setScale(scale);
		stage.addActor(sky);
		sky.setHeight(Zone.RAIN.getY() * 100);
		sky.setWidth(Constants.WIDTH);

		mountains = new Image(Icons.getTex("bg/mountain.png"));
		mountains.setScale(scale);
		mountains.setWidth(Constants.WIDTH);

		stage.addActor(mountains);
		rocks = new Image(Icons.getTex("bg/bigrock.png"));
		rocks.setScale(scale);
		rocks.setY(230f * scale);
		rocks.setWidth(Constants.WIDTH);
		mountains.setY(rocks.getY() + 256f * scale);
		stage.addActor(rocks);

		if (scaled) {
			ground = new Ground("bg/ground.png", world);

			ground.bodyDef.type = BodyType.StaticBody;
			ground.body.setTransform(ground.getWidth() / 2, ground.boxHeight / 2, 0);
			stage.addActor(ground);
			ground.setWidth(Constants.SCLWIDTH);
		} else {
			Image ground = new Image(Icons.getTex("bg/ground.png"));
			ground.setY(0);
			stage.addActor(ground);
			ground.setWidth(Constants.WIDTH);
		}
		
		

	}
}
