package com.jumpbuttonstudios.totemgame;

import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Sort;

public class HighScores {
	public static int[] scores = new int[15];
	private static Json json;

	public static void init() {
// scores = new int[10];
	}

	public static void addScore(int newScore) {

		Integer[] conversion = new Integer[15];
		for (int i = 0; i < scores.length; i++) {
			conversion[i] = Integer.valueOf(scores[i]);
		}

		Arrays.sort(conversion, Collections.reverseOrder());

		for (int i = 0; i < conversion.length; i++) {
			scores[i] = Integer.valueOf(conversion[i]);
		}

		for (int i = 0; i < scores.length; i++) {
			if (newScore > scores[i]) {
				System.arraycopy(scores, i, scores, i + 1, scores.length - i - 1);
				scores[i] = newScore;
				break;
			}
		}
		write();
	}

	public static void read() {
		FileHandle file = Gdx.files.local("highScores.json");
		if (file.exists()) {
			String scoresString = file.readString();
			Json json = new Json();
			Array<Integer> scoreList = json.fromJson(Array.class, Integer.class, file);

			for (int i = 0; i < scoreList.size; i++) {
				scores[i] = Integer.valueOf(scoreList.get(i));
			}
		} else {

			for (int i = 0; i < scores.length; i++) {
				scores[i] = 0;
			}
		}
	}

	public static void write() {
		Json json = new Json();
		FileHandle file = Gdx.files.local("highScores.json");
		String highScores = json.toJson(scores);
		file.writeString(highScores, false);
	}

	public static void setScoreLength(int length) {
		scores = new int[length];
	}

}
