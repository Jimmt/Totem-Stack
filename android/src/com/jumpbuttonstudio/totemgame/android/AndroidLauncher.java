package com.jumpbuttonstudio.totemgame.android;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.example.games.basegameutils.GameHelper;
import com.jumpbuttonstudio.totemgame.GamePrefs;
import com.jumpbuttonstudio.totemgame.IabInterface;
import com.jumpbuttonstudio.totemgame.TotemGame;
import com.jumpbuttonstudio.totemgame.android.util.IabHelper;
import com.jumpbuttonstudio.totemgame.android.util.IabHelper.OnConsumeFinishedListener;
import com.jumpbuttonstudio.totemgame.android.util.IabResult;
import com.jumpbuttonstudio.totemgame.android.util.Inventory;
import com.jumpbuttonstudio.totemgame.android.util.Purchase;

public class AndroidLauncher extends AndroidApplication implements IabInterface {
	private IabHelper mHelper;
	private GameHelper helper;
	private TotemGame game;
	private AndroidServices services;
	private final String BANNER_ID = "ca-app-pub-8823077351295808/1547261578";
	private final String INTERSTITIAL_ID = "ca-app-pub-8823077351295808/4500727979";
	public InterstitialAd interstitialAd;
	protected AdView adView, admobView;
	protected View gameView;
	private RelativeLayout layout;
	private boolean displayAds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAun/GajJbONdrpBzIITyxFcvyfVIkuBZAwK7rUDtvizr5HfcTN4AB4W7Chigr3n3vMaq6LBiwLS5xAZ+4TwGtmykR6S2e2aNNhVb8RL7alI7npV9PYLxclWnmq/SWI6uAvxZ0H2szOD1jOxaWPdBLbr6Uf9WGXVRORFPfboKh26/pG4LW2lgF0sL51y1rhjGn032nvQiFJWV1ZeHoi1UoWxC05Sf4flzkCoHBQDneRrvnPoFfBAg/pRl92E+A3vlNA57NWimRkfWNtbWM8EqMHgjWBBW4uNGZ7fzE4ROjBYeWNzxSRaB22/WJaCbQTsJiYUD/2G8l2ixReAWMpvp1CwIDAQAB";

		// compute your public key and store it in base64EncodedPublicKey
		mHelper = new IabHelper(this, base64EncodedPublicKey);

		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					Log.d("IAB", "Problem setting up In-app Billing: " + result);
				}
				if (mHelper == null)
					return;
				// Hooray, IAB is fully set up!
				Log.d("IAB", "Billing Success: " + result);
// ArrayList<String> skulist = new ArrayList<String>();
// skulist.add(SKU_TEST);
				mHelper.queryInventoryAsync(mGotInventoryListener);
// removeAds();
			}

		});

		helper = new GameHelper(this, GameHelper.CLIENT_GAMES);

		services = new AndroidServices(helper, this);
		helper.setup(services);
		helper.enableDebugLog(true);

		game = new TotemGame(services);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// initialize(new PuckSlide(services), config);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);

		admobView = createAdView();
		layout.addView(admobView);
		View gameView = createGameView(config);
		layout.addView(gameView);

		setContentView(layout);
		startAdvertising(admobView);

		interstitialAd = new InterstitialAd(this);

		interstitialAd.setAdUnitId(INTERSTITIAL_ID);
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				// Toast.makeText(getApplicationContext(),
// "Finished Loading Interstitial",
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdClosed() {
				// Toast.makeText(getApplicationContext(),
// "Closed Interstitial",
				// Toast.LENGTH_SHORT)
				// .show();
			}
		});

	}

	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
			Log.d("IAB", "Query inventory finished.");

			// Have we been disposed of in the meantime? If so, quit.
			if (mHelper == null)
				return;

			// Is it a failure?
			if (result.isFailure()) {
				Log.d("IAB", "Failed to query inventory: " + result);
				return;
			}

			Log.d("IAB", "Query inventory was successful.");

			/*
			 * Check for items we own. Notice that for each purchase, we check
			 * the developer payload to see if it's correct! See
			 * verifyDeveloperPayload().
			 */

			// Do we have the premium upgrade?
			Purchase purchase = inventory.getPurchase("remove_ads");
			displayAds = !(purchase != null);
			Log.d("IAB", displayAds ? "Displaying ads" : "Not displaying ads");

			if (!displayAds) {
				layout.removeView(admobView);
			}
		}
	};

	private AdView createAdView() {
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(BANNER_ID);
		adView.setId(12345); // this is an arbitrary id, allows for relative
// positioning in createGameView()
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		adView.setLayoutParams(params);
		adView.setBackgroundColor(Color.BLACK);
		return adView;
	}

	private View createGameView(AndroidApplicationConfiguration cfg) {
		gameView = initializeForView(new TotemGame(services), cfg);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.BELOW, adView.getId());
		gameView.setLayoutParams(params);
		return gameView;
	}

	private void startAdvertising(AdView adView) {
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null)
			mHelper.dispose();
		mHelper = null;

		android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("IAB", "onActivityResult(" + requestCode + "," + resultCode + "," + data);
		super.onActivityResult(requestCode, resultCode, data);
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d("IAB", "onActivityResult handled by IABUtil.");
		}
		helper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean getDisplayAds() {
		return displayAds;
	}

	@Override
	public void removeAds() {
		mHelper.launchPurchaseFlow(this, SKU_REMOVE_ADS, RC_REQUEST,
				new IabHelper.OnIabPurchaseFinishedListener() {

					@Override
					public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
						// if we were disposed of in the meantime, quit.
						if (mHelper == null) {
							Log.d("IAB", "mHelper is null");
							return;
						}

						if (result.isFailure()) {
							Log.d("IAB", "Error purchainsg: " + purchase);
							return;
						}

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								layout.removeView(adView);
							}
						});

					}
				}, "HANDLE_PAYLOADS");
	}

	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			if (mHelper == null)
				return;
			if (result.isSuccess()) {
				int amount = 0;
				if (purchase.getSku().endsWith("500")) {
					amount = 500;
				}
				if (purchase.getSku().endsWith("1750")) {
					amount = 1750;
				}
				if (purchase.getSku().endsWith("4000")) {
					amount = 4000;
				}

				GamePrefs.putInteger("coins", GamePrefs.prefs.getInteger("coins") + amount);
				Log.d("IAB", "Consumed");
			} else {
				Log.d("IAB", "Consume not success");
			}
		}
	};

	@Override
	public void buyCoins(final int amount) {

// mHelper.launchPurchaseFlow(this, SKU_TEST, RC_REQUEST,
// new IabHelper.OnIabPurchaseFinishedListener() {
//
// @Override
// public void onIabPurchaseFinished(IabResult result, Purchase info) {
// Log.d("IAB", info.getSku()); // info is null
// Log.d("IAB", info.getItemType());
//
// }
// }, "HANDLE_PAYLOADS");

		mHelper.flagEndAsync();
		String SKU_CODE = "coins_" + amount;
		Log.d("IAB", "Buying");
		mHelper.launchPurchaseFlow(this, SKU_CODE, RC_REQUEST,
				new IabHelper.OnIabPurchaseFinishedListener() {

					@Override
					public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
						Log.d("IAB", "Purchase finished: " + result + ", purchase: " + purchase);

						// if we were disposed of in the meantime, quit.
						if (mHelper == null) {
							Log.d("IAB", "mHelper is null");
							return;
						}

						// android.test.purchased: purchase is null

						if (result.isFailure()) {
// mHelper.consumeAsync(purchase, mConsumeFinishedListener); // trying
// to consume android.test.purchased
							Log.d("IAB", "Error purchainsg: " + purchase);
							return;
						}
						mHelper.consumeAsync(purchase, mConsumeFinishedListener);

					}
				}, "HANDLE_PAYLOADS");

	}

}
