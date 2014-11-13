package com.jumpbuttonstudios.totemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class Icons {
	public static Image[] totemImages = new Image[4];
	public static Image[] parachuteImages = new Image[4];
	public static Image[] normalPoints = new Image[3];
	public static Image[] doublePoints = new Image[3];

	public static TextureRegion[][] iceImagesArr;
	public static Array<TextureRegion> iceImages;

	public static ObjectMap<String, Image> images;

	public static FileHandle listFile = Gdx.files.local("file.txt");

	public static Image returnImage(String path) {
		Image i = new Image(new Texture(Gdx.files.internal(path)));
		i.setScale(Constants.SCALE);
		return i;
	}

	public static Image getImage(String path) {
		return images.get(path);
	}

	public static void search(FileHandle[] files) {

		for (int i = 0; i < files.length; i++) {
			if (files[i].extension().equals("png")) {
				String uri = files[i].toString().replace("./bin/", "");
// listFile.writeString(uri + "\n", true);
				images.put(uri, returnImage(files[i].toString()));
			}
			if (files[i].isDirectory()) {
				search(files[i].list());
			}
		}

	}

	public static void loadIcons() {
		images = new ObjectMap<String, Image>();

		iceImages = new Array<TextureRegion>();

		FileHandle master;
		if (Gdx.app.getType() == ApplicationType.Android) {
			master = Gdx.files.internal("");
			search(master.list());

		} else {
			master = Gdx.files.internal("./bin/");

			String[] listPaths = listFile.readString().split("\n");
//			for (int i = 0; i < listPaths.length; i++) {
//				images.put(listPaths[i], returnImage(listPaths[i])); enable for desktop builds
//			}
			search(master.list()); //disable for desktop builds
		}

		iceImagesArr = new TextureRegion(new Texture("totem/ice/Trail_Packed.png")).split(63, 62);

		for (int i = 0; i < iceImagesArr[0].length; i++) {
			TextureRegion tex = iceImagesArr[0][i];
			iceImages.add(tex);
		}

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
