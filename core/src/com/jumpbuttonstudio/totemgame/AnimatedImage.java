package com.jumpbuttonstudio.totemgame;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatedImage extends Actor {
	AnimatedSprite animatedSprite;

	public AnimatedImage(String path, float speed, int width, int height, float x, float y) {
		super();
		
		animatedSprite = AnimationBuilder.buildAnimatedSprite(path, speed, width, height);
		animatedSprite.setPosition(x, y);
		animatedSprite.setKeepSize(true);

	}

	@Override
	public void act(float delta){
		super.act(delta);
		
		if (animatedSprite.getTime() >= animatedSprite
				.getAnimation().getAnimationDuration()
				- animatedSprite.getAnimation().getFrameDuration()) {
			animatedSprite.setAlpha(0.0f);
			animatedSprite.stop();
		}
	}
	
	public void play(){
		animatedSprite.setAlpha(1.0f);
		animatedSprite.play();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		animatedSprite.draw(batch);

	}
}
