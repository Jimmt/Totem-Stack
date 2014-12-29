package com.jumpbuttonstudios.totemgame;

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
//	{ "bg/bigrock.png", "bg/Clouds00.png", "bg/Clouds01.png",
//			"bg/Clouds02.png", "bg/Clouds03.png", "bg/fog.png", "bg/ground.png", "bg/mountain.png",
//			"bg/net.png", "bg/skygradient.png", "bg/stars.png", "black.png", "blank.png",
//			"drape.png", "effects/dust.png", "effects/particle.png", "effects/perfectland.png",
//			"effects/Stars.png", "flash.png", "gameover/bestscore.png",
//			"gameover/bestScoreBox.png", "gameover/box.png", "gameover/gameover.png",
//			"gameover/mock.png", "gameover/window.png", "gameover/yourscore.png",
//			"gameover/yourScoreBox.png", "good.png", "login/avatar.png", "login/entrysep.png",
//			"login/font.png", "login/highscore.png", "login/highscore_clicked.png",
//			"login/login.png", "login/login_clicked.png", "login/logout.png",
//			"login/logoutwindow.png", "login/logout_clicked.png", "login/mock/VD.png",
//			"login/mock/VD2.png", "login/mock/VD3.png", "login/mock/VD4.png", "login/mock/VD5.png",
//			"login/mock/VD6.png", "login/ok.png", "login/ok_pressed.png", "login/register.png",
//			"login/register_pressed.png", "login/signin.png", "login/signin_pressed.png",
//			"login/start.png", "login/start_clicked.png", "login/welcomeback.png",
//			"login/window.png", "login/wrong.png", "login/yes.png", "login/yes_pressed.png",
//			"logo.png", "perfect.png", "shop/add.png", "shop/add_pressed.png", "shop/board.png",
//			"shop/boardNew.png", "shop/button_normal.png", "shop/button_pressed.png",
//			"shop/coin.png", "shop/jbscoin.png", "shop/mock.png", "shop/paper.png",
//			"skin/default.png", "skin/uiskin.png", "totem/00.png", "totem/01.png", "totem/02.png",
//			"totem/03.png", "totem/flag.png", "totem/ice/freeze.png", "totem/ice/ice.png",
//			"totem/ice/icenormal.png", "totem/ice/iceparachute.png", "totem/ice/Trail_Packed.png",
//			"totem/parachute.png", "totem/parachute00.png", "totem/parachute01.png",
//			"totem/parachute02.png", "totem/parachute03.png", "totem/special/parachutespec.png",
//			"totem/special/special.png", "totem/special/specialshine.png",
//			"totem/special/Stars.png", "ts compl/00.png", "ts compl/01.png", "ts compl/02.png",
//			"ts compl/03.png", "ui/gameover/achievements.png",
//			"ui/gameover/achievements_pressed.png", "ui/gameover/disabled.png",
//			"ui/gameover/facebook.png", "ui/gameover/facebook_pressed.png",
//			"ui/gameover/gowindow.png", "ui/gameover/highscores.png",
//			"ui/gameover/highscores_pressed.png", "ui/gameover/replay.png",
//			"ui/gameover/replay_pressed.png", "ui/gameover/scorefont.png",
//			"ui/gameover/textfield.png", "ui/gameover/twitter.png",
//			"ui/gameover/twitter_pressed.png", "ui/gameplay/+1.png", "ui/gameplay/+2.png",
//			"ui/gameplay/+2double.png", "ui/gameplay/+3.png", "ui/gameplay/+4double.png",
//			"ui/gameplay/+6double.png", "ui/gameplay/left.png", "ui/gameplay/leftclicked.png",
//			"ui/gameplay/right.png", "ui/gameplay/rightclicked.png", "ui/highscore/back.png",
//			"ui/highscore/back_pressed.png", "ui/highscore/border.png", "ui/highscore/font_0.png",
//			"ui/highscore/friends.png", "ui/highscore/friendsdisable.png",
//			"ui/highscore/global.png", "ui/highscore/globaldisable.png",
//			"ui/highscore/personal.png", "ui/highscore/personaldisable.png",
//			"ui/highscore/rank1.png", "ui/highscore/ranks.png", "ui/highscore/ray.png",
//			"ui/highscore/window.png", "ui/options/check.png", "ui/options/checkbg.png",
//			"ui/options/close.png", "ui/options/settings.png", "ui/options/tap.png",
//			"ui/options/tilt.png", "ui/options/window.png", "ui/options/windowblank.png",
//			"ui/options/x.png", "ui/options/xbg.png", "ui/paused/removead.png",
//			"ui/paused/resumebutton.png", "ui/paused/resumewindow.png",
//			"ui/paused/resume_pressed.png", "ui/shop/100.png", "ui/shop/1750jbs.png",
//			"ui/shop/1dollar.png", "ui/shop/200.png", "ui/shop/300.png", "ui/shop/3dollar.png",
//			"ui/shop/4000jbs.png", "ui/shop/500.png", "ui/shop/500jbs.png", "ui/shop/7dollar.png",
//			"ui/shop/buyitem.png", "ui/shop/buyitem_pressed.png", "ui/shop/buyjbs.png",
//			"ui/shop/buyjbs_pressed.png", "ui/shop/close.png", "ui/shop/freeze.png",
//			"ui/shop/freezeButton.png", "ui/shop/freezeinfo.png", "ui/shop/freezeSelected.png",
//			"ui/shop/jbscoin.png", "ui/shop/jbscoin_0000_Layer-3.png",
//			"ui/shop/jbscoin_0001_Layer-2.png", "ui/shop/jbscoin_0002_Layer-1.png",
//			"ui/shop/num.png", "ui/shop/retry.png", "ui/shop/retryButton.png",
//			"ui/shop/retryinfo.png", "ui/shop/retrySelected.png", "ui/shop/selected.png",
//			"ui/shop/slow.png", "ui/shop/slowButton.png", "ui/shop/slowinfo.png",
//			"ui/shop/slowSelected.png", "ui/shop/unselected.png", "ui/shop/wind.png",
//			"ui/shop/windButton.png", "ui/shop/windinfo.png", "ui/shop/window.png",
//			"ui/shop/window01.png", "ui/shop/windSelected.png", "ui/top/header.png",
//			"ui/top/home.png", "ui/top/home_pressed.png", "ui/top/pause.png",
//			"ui/top/pause_pressed.png", "ui/top/score.png", "ui/top/score_spacing.png",
//			"ui/top/setting.png", "ui/top/setting_pressed.png", "ui/top/shop.png",
//			"ui/top/shop_pressed.png", "ui/top/soundoff.png", "ui/top/soundoff_pressed.png",
//			"ui/top/soundon.png", "ui/top/soundon_pressed.png", };

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
