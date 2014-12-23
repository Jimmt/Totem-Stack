package com.jumpbuttonstudios.totemgame.android;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jumpbuttonstudios.totemgame.GamePrefs;
import com.jumpbuttonstudios.totemgame.TotemGame;
import com.jumpbuttonstudios.totemgame.android.util.IabHelper;
import com.jumpbuttonstudios.totemgame.android.util.IabResult;
import com.jumpbuttonstudios.totemgame.android.util.Purchase;

public class AndroidLauncher extends AndroidApplication implements IabInterface {
	IabHelper mHelper;
	boolean test;
	TotemGame game;

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
				buyCoins(123);
			}
			
		});

		game = new TotemGame();
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
	public void buyCoins(final int amount) {

		mHelper.launchPurchaseFlow(this, SKU_BUY_COINS, RC_REQUEST,
				new IabHelper.OnIabPurchaseFinishedListener() {
			
					@Override
					public void onIabPurchaseFinished(IabResult result, Purchase info) {
						test = true;
						
//						if(info.getSku().equals(SKU_BUY_COINS)){
							Log.d("IAB", info.getSku()); //info is null
							Log.d("IAB", "Buying " + amount + " coins");
							GamePrefs.putInteger("coins", GamePrefs.prefs.getInteger("coins") + amount);
//						}
					}
				}, "HANDLE_PAYLOADS");

	}

}
