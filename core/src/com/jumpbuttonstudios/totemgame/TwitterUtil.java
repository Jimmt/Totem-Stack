package com.jumpbuttonstudios.totemgame;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import twitter4j.TwitterException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;

public class TwitterUtil {
	private final static String CONSUMER_KEY = "ML6Yz2T3xlt5MKDdtcfmb22Th";
	private final static String CONSUMER_KEY_SECRET = "p1LWpMN4juOpdRbsNgKMlvg1JyOqpLyU6lHIxGLTg26OOZOO6Z";

	public static void post(String text) throws TwitterException, IOException {

		Map parameters = new HashMap();
		parameters.put("oauth_callback", "http://jumpbuttonstudio.com");

		HttpRequest httpPost = new HttpRequest(HttpMethods.GET);
		httpPost.setUrl("https://api.twitter.com/oauth/request_token");
		httpPost.setContent(HttpParametersUtils.convertHttpParameters(parameters));

		Gdx.net.sendHttpRequest(httpPost, new HttpResponseListener() {
			public void handleHttpResponse(HttpResponse httpResponse) {
				String status = httpResponse.getResultAsString();
				System.out.println(status);
				// do stuff here based on response
			}

			public void failed(Throwable t) {
				String status = "failed";

				// do stuff here based on the failed attempt
			}

			@Override
			public void cancelled() {
				// TODO Auto-generated method stub

			}
		});

// Twitter twitter = new TwitterFactory().getInstance();
// twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
// RequestToken requestToken = twitter.getOAuthRequestToken();
// System.out.println("Authorization URL: \n" +
// requestToken.getAuthorizationURL());
//
// Gdx.net.openURI(requestToken.getAuthorizationURL());
//
//
// AccessToken accessToken = null;
//
// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
// while (null == accessToken) {
// try {
// System.out.print("Input PIN here: ");
// String pin = br.readLine();
//
// accessToken = twitter.getOAuthAccessToken(requestToken, pin);
//
// } catch (TwitterException te) {
//
// System.out.println("Failed to get access token, caused by: " +
// te.getMessage());
//
// System.out.println("Retry input PIN");
//
// }
// }
//
//
//
// System.out.println("Access Token: " + accessToken.getToken());
// System.out.println("Access Token Secret: " + accessToken.getTokenSecret());
//
// twitter.setOAuthAccessToken(accessToken);
//
// twitter.updateStatus(text);

	}

}