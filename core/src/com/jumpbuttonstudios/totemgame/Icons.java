package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Icons {
	public static Image one, two, three, twoDouble, fourDouble, sixDouble, perfect, good;
	public static Image parachute;
	public static Image left, right, leftP, rightP;
	public static Image[] totemImages = new Image[4]; 
	public static Image[] parachuteImages = new Image[4]; 
	public static Image[] normalPoints = new Image[3];
	public static Image[] doublePoints = new Image[3];

	public static Image returnImage(String path) {
		Image i = new Image(new Texture(Gdx.files.internal(path)));
		i.setScale(Constants.SCALE);
		return i;
	}

	public static void loadIcons() {
		left = Icons.returnImage("ui/gameplay/left.png");
		leftP = Icons.returnImage("ui/gameplay/leftclicked.png");
		right = Icons.returnImage("ui/gameplay/right.png");
		rightP = Icons.returnImage("ui/gameplay/rightclicked.png");
		one = Icons.returnImage("ui/gameplay/+1.png");
		two = Icons.returnImage("ui/gameplay/+2.png");
		twoDouble = Icons.returnImage("ui/gameplay/+2double.png");
		three = Icons.returnImage("ui/gameplay/+3.png");
		fourDouble = Icons.returnImage("ui/gameplay/+4double.png");
		sixDouble = Icons.returnImage("ui/gameplay/+6double.png");
		perfect = Icons.returnImage("perfect.png");
		good = Icons.returnImage("good.png");
		
		parachute = Icons.returnImage("totem/parachute.png");

		normalPoints[0] = one;
		normalPoints[1] = two;
		normalPoints[2] = three;

		doublePoints[0] = twoDouble;
		doublePoints[1] = fourDouble;
		doublePoints[2] = sixDouble;
		
		for(int i = 0; i < 4; i++){
			totemImages[i] = Icons.returnImage("totem/0" + i + ".png");
		}
		for(int i = 0; i < 4; i++){
			parachuteImages[i] = Icons.returnImage("totem/parachute0" + i + ".png");
		}
	}

}
