package com.jumpbuttonstudio.totemgame;

import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TwitterUtil {
	private final static String CONSUMER_KEY = "ML6Yz2T3xlt5MKDdtcfmb22Th";
	private final static String CONSUMER_KEY_SECRET = "p1LWpMN4juOpdRbsNgKMlvg1JyOqpLyU6lHIxGLTg26OOZOO6Z";
	private static String pin;
	public static AccessToken accessToken = null;
	public static Twitter twitter;
	public static RequestToken requestToken;
	public static boolean loggedIn;

	public static void init() throws TwitterException, IOException {

		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

	}

	public static void openURL() {
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		Gdx.net.openURI(requestToken.getAuthorizationURL());
		
	}

	public static void postFinal(String text) {
		twitter.setOAuthAccessToken(accessToken);

		try {
			twitter.updateStatus(text);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public static void setPin(String newPin) {
		pin = newPin;
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			loggedIn = true;
		} catch (TwitterException e) {
			loggedIn = false;
			e.printStackTrace();
		}
	}

}