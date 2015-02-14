package com.jumpbuttonstudios.totemgame;

import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Sort;

public class HighScores {
	public static Array<Integer> scores = new Array<Integer>();
	private static Json json;
	private static int length = 15;

	public static void init() {

	}

	public static void addScore(int newScore) {
		scores.add(newScore);

		Sort.instance().sort(scores, Collections.reverseOrder());

		for (int i = length; i < scores.size; i++) {
			scores.removeIndex(i);
		}
		
		write();
	}

	public static void read() {
		FileHandle file = Gdx.files.local("highScores.json");
		if (file.exists()) {
			String scoresString = file.readString();
			Json json = new Json();
			scores = json.fromJson(Array.class, Integer.class, file);

		} else {

			for (int i = 0; i < length; i++) {
				scores.add(0);
			}
		}
	}

	public static void write() {
		Json json = new Json();
		FileHandle file = Gdx.files.local("highScores.json");
		String highScores = json.toJson(scores);
		file.writeString(highScores, false);
	}

	public static int getScoreLength() {
		return length;
	}

	public static void setScoreLength(int slength) {
		length = slength;
	}

}
