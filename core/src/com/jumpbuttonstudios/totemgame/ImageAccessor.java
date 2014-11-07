package com.jumpbuttonstudios.totemgame;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageAccessor implements TweenAccessor<Image> {

	@Override
	public int getValues(Image target, int tweenType, float[] returnValues) {
		returnValues[0] = target.getX();
		returnValues[1] = target.getY();
		return 2;
	}

	@Override
	public void setValues(Image target, int tweenType, float[] newValues) {
		target.setX(newValues[0]);
		target.setY(newValues[1]);
		System.out.println(newValues);
		
	}

}
