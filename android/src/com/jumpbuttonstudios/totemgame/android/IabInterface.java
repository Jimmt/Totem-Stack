package com.jumpbuttonstudios.totemgame.android;

public interface IabInterface {
	
	public String SKU_BUY_COINS_500 = "coins_500";
	public String SKU_REMOVE_ADS = "remove_ads";
//	public String SKU_BUY_COINS = "android.test.purchased";
	
	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;

	public void buyCoins(int amount);
	public void removeAds();
}
