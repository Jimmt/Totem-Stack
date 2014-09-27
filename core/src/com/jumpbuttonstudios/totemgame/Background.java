package com.jumpbuttonstudios.totemgame;

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
		
		sky = new Image(new Texture(Gdx.files.internal("bg/skygradient.png")));
		sky.setScale(Constants.SCALE);
		stage.addActor(sky);
		sky.setHeight(Constants.HEIGHT * 4);
		sky.setWidth(Constants.WIDTH);	
		
		mountains = new Image(new Texture(Gdx.files.internal("bg/mountain.png")));
		mountains.setScale(Constants.SCALE);
		
		stage.addActor(mountains);
		rocks = new Image(new Texture(Gdx.files.internal("bg/bigrock.png")));
		rocks.setScale(Constants.SCALE);
		rocks.setY(230f * Constants.SCALE);
		mountains.setY(rocks.getY() + 256f * Constants.SCALE);
		stage.addActor(rocks);

		ground = new Ground("bg/ground.png", world);
		ground.bodyDef.type = BodyType.StaticBody;
		ground.body.setTransform(ground.getWidth() / 2, ground.boxHeight / 2, 0);
		stage.addActor(ground);

	}
}
