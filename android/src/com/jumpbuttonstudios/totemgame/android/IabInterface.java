package com.jumpbuttonstudios.totemgame.android;

public interface IabInterface {
	
//	public String SKU_BUY_COINS = "buy_coins";
	public String SKU_BUY_COINS = "android.test.purchased";
	
	// (arbitrary) request code for the purchase flow
	static final int RC_REQUEST = 10001;

	public void buyCoins(int amount);
}
