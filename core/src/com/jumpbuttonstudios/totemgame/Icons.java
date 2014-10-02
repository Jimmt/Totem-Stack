package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Icons {
	public static Image one, two, three, twoDouble, fourDouble, sixDouble, perfect, good;
	public static Image[] normalPoints = new Image[3];
	public static Image[] doublePoints = new Image[3];

	public static Image returnImage(String path) {
		Image i = new Image(new Texture(Gdx.files.internal(path)));
		i.setScale(Constants.SCALE);
		return i;
	}

	public static void loadIcons() {
		one = Icons.returnImage("ui/gameplay/+1.png");
		two = Icons.returnImage("ui/gameplay/+2.png");
		twoDouble = Icons.returnImage("ui/gameplay/+2double.png");
		three = Icons.returnImage("ui/gameplay/+3.png");
		fourDouble = Icons.returnImage("ui/gameplay/+4double.png");
		sixDouble = Icons.returnImage("ui/gameplay/+6double.png");
		perfect = Icons.returnImage("perfect.png");
		good = Icons.returnImage("good.png");

		normalPoints[0] = one;
		normalPoints[1] = two;
		normalPoints[2] = three;

		doublePoints[0] = twoDouble;
		doublePoints[1] = fourDouble;
		doublePoints[2] = sixDouble;
	}

}
