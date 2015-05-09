package com.jumpbuttonstudio.totemgame;

import java.util.HashMap;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class Icons {
	public static Image[] totemImages = new Image[4];
	public static Image[] parachuteImages = new Image[4];
	public static Image[] normalPoints = new Image[3];
	public static Image[] doublePoints = new Image[3];

	public static String[] paths = {"totem/00.png", "totem/01.png", "totem/02.png",
		"totem/03.png", "totem/flag.png", "totem/ice/freeze.png", "totem/ice/ice.png",
		"totem/ice/icenormal.png", "totem/ice/iceparachute.png", "totem/ice/Trail_Packed.png",
		"totem/parachute.png", "totem/parachute00.png", "totem/parachute01.png",
		"totem/parachute02.png", "totem/parachute03.png", "totem/special/parachutespec.png",
		"totem/special/special.png", "totem/special/specialshine.png",
		"totem/special/Stars.png", "ts compl/00.png", "ts compl/01.png", "ts compl/02.png",
		"ts compl/03.png", "ui/gameplay/+1.png", "ui/gameplay/+2.png",
		"ui/gameplay/+2double.png", "ui/gameplay/+3.png", "ui/gameplay/+4double.png",
		"ui/gameplay/+6double.png", "perfect.png", "good.png"};

	public static TextureRegion[][] iceImagesArr;
	public static Array<TextureRegion> iceImages;

	public static ObjectMap<String, Image> images;

	public static FileHandle listFile = Gdx.files.local("file.txt");

	static HashMap<String,Texture> textureCache = new HashMap<String,Texture>();
	
	/**
	 * returns a new texture from internal file system or a cached texture
	 * if its already been loaded
	 * @param path the file path to load
	 * @param fileType the file path resolver to load with
	 * @return
	 */
	public static Texture getTex( String path, FileType fileType ) {
		if(textureCache.containsKey(path))
			return textureCache.get(path);

		Texture tex = new Texture(Gdx.files.getFileHandle(path, fileType));
		tex.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		textureCache.put(path, tex);
		return tex;
	}
	/**
	 * returns a new texture from internal file system or a cached texture
	 * if its already been loaded
	 * @param path the file path to load
	 * @return
	 */
	public static Texture getTex( String path ) {
		return getTex( path, FileType.Internal );
	}
	
	public static Image returnImage(String path) {
		Texture tex = new Texture(Gdx.files.internal(path));
		//this is how you smooth textures with AA
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Image i = new Image( tex );
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
 
//		FileHandle master;
//		if (Gdx.app.getType() == ApplicationType.Android) {
//			master = Gdx.files.internal("");
//			search(master.list());
//
//		} else {
//			master = Gdx.files.internal("./bin/");
//
//			String[] listPaths = listFile.readString().split("\n");
//			for (int i = 0; i < listPaths.length; i++) {
//				images.put(listPaths[i], returnImage(listPaths[i])); // enable
//// for desktop builds
//			}
//			// search(master.list()); //disable for desktop builds
//		}
		

		for (int i = 0; i < paths.length; i++) {
			images.put(paths[i], returnImage(paths[i]));
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
