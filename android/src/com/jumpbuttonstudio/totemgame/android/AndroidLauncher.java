package com.jumpbuttonstudio.totemgame.android;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.example.games.basegameutils.GameHelper;
import com.jumpbuttonstudio.totemgame.GamePrefs;
import com.jumpbuttonstudio.totemgame.IabInterface;
import com.jumpbuttonstudio.totemgame.TotemGame;
import com.jumpbuttonstudio.totemgame.android.util.IabHelper;
import com.jumpbuttonstudio.totemgame.android.util.IabResult;
import com.jumpbuttonstudio.totemgame.android.util.Purchase;

public class AndroidLauncher extends AndroidApplication implements IabInterface {
	private IabHelper mHelper;
	private GameHelper helper;
	private TotemGame game;
	private AndroidServices services;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzPPYdyceSscXvMRBLYug9+V1SUJ9pVMfUjU4K1yNLK0i5e/lW16BOWivcXo/OjfrmGTI6mwYFRAw16rcwbZyWRce/NLpCBiEk3aBHDaWPjmQgO1oPNUKi2XKuYYnHEUV70AVxaOYdpzi/LNwJYaoRqL+ho6F5sEeL0dfS2EhaNpX1zOrTSvI+Rz+mlpeOnTEr2aNyCOjIMhXtYdxgxtr1dJoATf6xcqFfMJ8enL+9BMP9Af+zyHrmxJvCX7BuAXR4E2fSlR3VD6zHSU1p9uAL4VkXk4LiX7esMy6qmzuYsQNVl7X3DjsVd1/hDvT1VsEPGcr+WdrKkxlIrq+WECoOwIDAQAB";

		// compute your public key and store it in base64EncodedPublicKey
		mHelper = new IabHelper(this, base64EncodedPublicKey);

		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					Log.d("IAB", "Problem setting up In-app Billing: " + result);
				}
				// Hooray, IAB is fully set up!
				Log.d("IAB", "Billing Success: " + result);
// removeAds();
			}

		});
		helper = new GameHelper(this, GameHelper.CLIENT_GAMES);

		services = new AndroidServices(helper, this);
		helper.setup(services);
		helper.enableDebugLog(true);
		
		game = new TotemGame(services);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(game, config);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null)
			mHelper.dispose();
		mHelper = null;
	}

	@Override
	public void removeAds() {
		mHelper.launchPurchaseFlow(this, SKU_REMOVE_ADS, RC_REQUEST,
				new IabHelper.OnIabPurchaseFinishedListener() {

					@Override
					public void onIabPurchaseFinished(IabResult result, Purchase info) {
						if (info.getSku().equals(SKU_REMOVE_ADS)) {

							Log.d("IAB", info.getSku());
							Log.d("IAB", "Removing ads");

						}
					}
				}, "HANDLE_PAYLOADS");
	}

	@Override
	public void buyCoins(final int amount) {

		
//		mHelper.launchPurchaseFlow(this, SKU_TEST, RC_REQUEST,
//				new IabHelper.OnIabPurchaseFinishedListener() {
//
//					@Override
//					public void onIabPurchaseFinished(IabResult result, Purchase info) {
//						test = true;
//						Log.d("IAB", info.getSku()); // info is null
//						Log.d("IAB", info.getItemType());
//
//					}
//				}, "HANDLE_PAYLOADS");
				
		if (amount == 500) {
			mHelper.launchPurchaseFlow(this, SKU_BUY_COINS_500, RC_REQUEST,
					new IabHelper.OnIabPurchaseFinishedListener() {

						@Override
						public void onIabPurchaseFinished(IabResult result, Purchase info) {
							Log.d("IAB", info.getSku()); // info is null
							Log.d("IAB", "Buying " + amount + " coins");
							GamePrefs.putInteger("coins", GamePrefs.prefs.getInteger("coins")
									+ amount);

						}
					}, "HANDLE_PAYLOADS");
		}
		
		if (amount == 1750) {
			mHelper.launchPurchaseFlow(this, SKU_BUY_COINS_1750, RC_REQUEST,
					new IabHelper.OnIabPurchaseFinishedListener() {

						@Override
						public void onIabPurchaseFinished(IabResult result, Purchase info) {
							Log.d("IAB", info.getSku()); // info is null
							Log.d("IAB", "Buying " + amount + " coins");
							GamePrefs.putInteger("coins", GamePrefs.prefs.getInteger("coins")
									+ amount);

						}
					}, "HANDLE_PAYLOADS");
		}
		
		if (amount == 4000) {
			mHelper.launchPurchaseFlow(this, SKU_BUY_COINS_4000, RC_REQUEST,
					new IabHelper.OnIabPurchaseFinishedListener() {

						@Override
						public void onIabPurchaseFinished(IabResult result, Purchase info) {
							Log.d("IAB", info.getSku()); // info is null
							Log.d("IAB", "Buying " + amount + " coins");
							GamePrefs.putInteger("coins", GamePrefs.prefs.getInteger("coins")
									+ amount);

						}
					}, "HANDLE_PAYLOADS");
		}

	}

}
