package com.jumpbuttonstudios.totemgame;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Flag extends Actor {
	AnimatedSprite animatedSprite;
	

	public Flag(float x, float y) {
		super();
		TextureRegion[][] regions = new TextureRegion(new Texture(Gdx.files.internal("totem/flag.png"))).split(130, 130);
		
		Animation animation = new Animation(1 / 5f, regions[0][0], regions[0][1], regions[0][2], regions[0][3], regions[0][4], regions[0][5]);
		animation.setPlayMode(PlayMode.LOOP);
		
		animatedSprite = new AnimatedSprite(animation);
		animatedSprite.setPosition(x, y);
		animatedSprite.play();
		animatedSprite.setSize(regions[0][0].getRegionWidth() * Constants.SCALE, regions[0][0].getRegionHeight() * Constants.SCALE);
		animatedSprite.setKeepSize(true);
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
		animatedSprite.draw(batch);
		
	}
	

}
