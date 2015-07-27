package com.jbs.totemgame;

import org.robovm.apple.adsupport.ASIdentifierManager;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.pods.chartboost.CBLocation;
import org.robovm.pods.chartboost.Chartboost;
import org.robovm.pods.chartboost.ChartboostDelegateAdapter;
//import org.robovm.pods.google.games.GPGManager;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

public class IOSLauncher extends IOSApplication.Delegate implements IGoogleServices{

	boolean signedIn;
	static final String kClientId = "306725650752-4b5fmus04ric4q73v30l498noal1h1g7.apps.googleusercontent.com";
	static final String LEAD_ID = "CgkIwIKY0vYIEAIQBg";
	IOSLauncher launcher;
	//GPGManager manager;

	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		launcher = this;
		Chartboost.start("55a880ecc909a61a908b7d24", "454b68dd8cf8a8fae8dcf67f0f03eba9c2a0fce6", null);
		//manager = new GPGManager();
		//manager.signIn();
		
		return new IOSApplication(new TotemGame(this), config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, IOSLauncher.class);
		pool.close();
	}

//	@Override
//	public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
//
//		// Begin a user session. Must not be dependent on user actions or any
//		// prior network requests.
//		Chartboost.start("55a880ecc909a61a908b7d24", "454b68dd8cf8a8fae8dcf67f0f03eba9c2a0fce6", null);
//		return true;
//		}
//
//	@Override
//	public void didBecomeActive(UIApplication application) {
//		
//	      
//
//	}
	@Override
	public void signIn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void signOut() {
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
	public void getAchievements() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlockAchievement(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getSignedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showOrLoadInterstitial() {
		Chartboost.showInterstitial(CBLocation.HomeScreen);
	}

	@Override
	public void removeAds() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getDisplayAds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void buyCoins(int amount) {
		// TODO Auto-generated method stub

	}

}