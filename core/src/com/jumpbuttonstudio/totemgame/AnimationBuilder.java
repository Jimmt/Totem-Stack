package com.jumpbuttonstudio.totemgame;

import net.dermetfan.utils.libgdx.graphics.AnimatedSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationBuilder {

	public static AnimatedSprite buildAnimatedSprite(String path, float speed, int width, int height) {
		TextureRegion[][] regions = new TextureRegion(new Texture(Gdx.files.internal(path)))
				.split(width, height);

		Array<TextureRegion> arr = new Array<TextureRegion>();

		for (int i = 0; i < regions.length; i++) {
			for (int j = 0; j < regions[0].length; j++) {
				arr.add(regions[i][j]);
			}
		}
		Animation animation = new Animation(speed, arr);

		AnimatedSprite sprite = new AnimatedSprite(animation);
		
		sprite.setSize(regions[0][0].getRegionWidth() * Constants.SCALE,
				regions[0][0].getRegionHeight() * Constants.SCALE);

		return sprite;
	}
}
