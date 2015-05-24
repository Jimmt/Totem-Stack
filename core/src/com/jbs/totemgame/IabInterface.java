package com.jbs.totemgame;

public interface IabInterface {
	
	public String SKU_BUY_COINS_500 = "coins_500";
	public String SKU_BUY_COINS_1750 = "coins_1750";
	public String SKU_BUY_COINS_4000 = "coins_4000";
	public String SKU_REMOVE_ADS = "remove_ads";
	public String SKU_TEST = "android.test.canceled";
	
	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;

	public void buyCoins(int amount);
	public void removeAds();
	public boolean getDisplayAds();
}
