package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ObjectMap;

public class Icons {
	public static Image[] totemImages = new Image[4];
	public static Image[] parachuteImages = new Image[4];
	public static Image[] normalPoints = new Image[3];
	public static Image[] doublePoints = new Image[3];
	public static String[] paths = { "ui/gameplay/left.png", "ui/gameplay/leftclicked.png",
			"ui/gameplay/right.png", "ui/gameplay/rightclicked.png", "ui/gameplay/+1.png",
			"ui/gameplay/+2.png", "ui/gameplay/+2double.png", "ui/gameplay/+3.png",
			"ui/gameplay/+4double.png", "ui/gameplay/+6double.png", "perfect.png", "good.png",
			"totem/parachute.png", "totem/special/specialshine.png", "ui/gameover/gowindow.png" };
	public static ObjectMap<String, Image> images;

	public static Image returnImage(String path) {
		Image i = new Image(new Texture(Gdx.files.internal(path)));
		i.setScale(Constants.SCALE);
		return i;
	}

	public static Image getImage(String path) {
		return images.get(path);
	}

	public static void loadIcons() {
		images = new ObjectMap<String, Image>();

		for (int i = 0; i < paths.length; i++) {
			images.put(paths[i], returnImage(paths[i]));
		}
// left = Icons.returnImage("ui/gameplay/left.png");
// leftP = Icons.returnImage("ui/gameplay/leftclicked.png");
// right = Icons.returnImage("ui/gameplay/right.png");
// rightP = Icons.returnImage("ui/gameplay/rightclicked.png");
// one = Icons.returnImage("ui/gameplay/+1.png");
// two = Icons.returnImage("ui/gameplay/+2.png");
// twoDouble = Icons.returnImage("ui/gameplay/+2double.png");
// three = Icons.returnImage("ui/gameplay/+3.png");
// fourDouble = Icons.returnImage("ui/gameplay/+4double.png");
// sixDouble = Icons.returnImage("ui/gameplay/+6double.png");
// perfect = Icons.returnImage("perfect.png");
// good = Icons.returnImage("good.png");
//
// parachute = Icons.returnImage("totem/parachute.png");

		normalPoints[0] = images.get("ui/gameplay/+1.png");
		normalPoints[1] = images.get("ui/gameplay/+2.png");
		normalPoints[2] = images.get("ui/gameplay/+3.png");

		doublePoints[0] = images.get("ui/gameplay/+2double.png");
		doublePoints[1] = images.get("ui/gameplay/+4double.png");
		doublePoints[2] = images.get("ui/gameplay/+6double.png");

		for (int i = 0; i < 4; i++) {
			totemImages[i] = Icons.returnImage("totem/0" + i + ".png");
		}
		for (int i = 0; i < 4; i++) {
			parachuteImages[i] = Icons.returnImage("totem/parachute0" + i + ".png");
		}
	}

}
