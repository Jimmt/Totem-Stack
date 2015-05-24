package com.jbs.totemgame;

public interface IGoogleServices {
	public void signIn();
	
	public void signOut();

	public void submitHighscore(int score);

	public void getLeaderboards();
	
	public void getAchievements();
	
	public void unlockAchievement(String id);

	public boolean getSignedIn();
	
	public void showOrLoadInterstitial();
	
	public void removeAds();
	
	public boolean getDisplayAds();
	
	public void buyCoins(int amount);
}