package com.jbs.totemgame;

public class DesktopGoogleServices implements IGoogleServices {

	@Override
	public void signIn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitHighscore(int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLeaderboards() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getSignedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlockAchievement(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAchievements() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showOrLoadInterstitial() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void signOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyCoins(int amount) {
		GamePrefs.prefs.putInteger("coins", GamePrefs.prefs.getInteger("coins") + amount);
		
	}

	@Override
	public boolean getDisplayAds() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
