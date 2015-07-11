package com.jbs.totemgame;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.bindings.gpgs.GPGAchievement;
import org.robovm.bindings.gpgs.GPGAchievementController;
import org.robovm.bindings.gpgs.GPGAchievementControllerDelegate;
import org.robovm.bindings.gpgs.GPGAchievementDidUnlockBlock;
import org.robovm.bindings.gpgs.GPGLeaderboardController;
import org.robovm.bindings.gpgs.GPGLeaderboardControllerDelegate;
import org.robovm.bindings.gpgs.GPGLeaderboardTimeScope;
import org.robovm.bindings.gpgs.GPGLeaderboardsController;
import org.robovm.bindings.gpgs.GPGLeaderboardsControllerDelegate;
import org.robovm.bindings.gpgs.GPGManager;
import org.robovm.bindings.gpgs.GPGReAuthenticationBlock;
import org.robovm.bindings.gpgs.GPGScore;
import org.robovm.bindings.gpgs.GPGScoreReport;
import org.robovm.bindings.gpgs.GPGScoreReportScoreBlock;
import org.robovm.bindings.gpp.GPPSignIn;
import org.robovm.bindings.gpp.GPPSignInDelegate;
import org.robovm.bindings.gt.GTMOAuth2Authentication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

public class IOSLauncher extends IOSApplication.Delegate implements IGoogleServices,
		GPPSignInDelegate, GPGAchievementControllerDelegate, GPGLeaderboardControllerDelegate,
		GPGLeaderboardsControllerDelegate {

	boolean signedIn;
	static final String kClientId = "306725650752-4b5fmus04ric4q73v30l498noal1h1g7.apps.googleusercontent.com";
	static final String LEAD_ID = "CgkIwIKY0vYIEAIQBg";
	private UIViewController viewController;
	private GPGScoreReportScoreBlock postScoreCompletionHandler;
	private GPGAchievementDidUnlockBlock unlockBlock;
	IOSLauncher launcher;

	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		launcher = this;
		viewController = new UIViewController();

		return new IOSApplication(new TotemGame(this), config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, IOSLauncher.class);
		pool.close();
	}

	@Override
	public void signIn() {
		GPPSignIn.sharedInstance().authenticate();
	}

	@Override
	public void signOut() {
		GPPSignIn signIn = GPPSignIn.sharedInstance();
		signIn.signOut();
		GPGManager m = GPGManager.sharedInstance();
		m.signOut();
		signedIn = false;
	}

	@Override
	public void submitHighscore(int score) {
		GPGScore gpgScore = new GPGScore(LEAD_ID);
		gpgScore.setValue(score);

		// set the completion handler
		postScoreCompletionHandler = new GPGScoreReportScoreBlock() {
			@Override
			public void invoke(GPGScoreReport report, NSError error) {
				if (error == null) {
					System.out.println("no errors");
				} else {
					System.out.println("score post failed: " + error.description());
				}
			}
		};

		gpgScore.submitScoreWithCompletionHandler(postScoreCompletionHandler);

	}

	@Override
	public void getLeaderboards() {
		if (signedIn) {
			System.out.println("Showing leaderboard");

			// create the view controller
			GPGLeaderboardController leadController = new GPGLeaderboardController(LEAD_ID);
			leadController.setLeaderboardDelegate(launcher);

			// you can choose the default time scope to display in the view
// controller.
			leadController.setTimeScope(GPGLeaderboardTimeScope.GPGLeaderboardTimeScopeThisWeek);

			// present the leaderboard view controller
			launcher.viewController.presentViewController(leadController, true, null);
		}

	}

	@Override
	public void getAchievements() {
		if (signedIn) {
			GPGAchievementController achController = new GPGAchievementController();
			achController.setAchievementDelegate(launcher);
			launcher.viewController.presentViewController(achController, true, null);
		}
	}

	@Override
	public void unlockAchievement(String id) {
		if (signedIn) {
			GPGAchievement ach = GPGAchievement.getAchievementWithId(id);
			unlockBlock = new GPGAchievementDidUnlockBlock() {
				@Override
				public void invoke(boolean newlyUnlocked, NSError error) {
					if (error != null) {
						System.out.println("Error while unlocking!");
					} else {
						System.out.println("unlock succeeded. newlyUnlocked: " + newlyUnlocked);
					}
				}
			};

			// unlock the achievement
			ach.unlockAchievementWithCompletionHandler(unlockBlock);
		}
	}

	@Override
	public boolean getSignedIn() {
		return signedIn;
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
	public boolean getDisplayAds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void buyCoins(int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaderboardsViewControllerDidFinish(GPGLeaderboardsController arg0) {
		viewController.dismissViewController(true, null);

	}

	@Override
	public void leaderboardViewControllerDidFinish(GPGLeaderboardController arg0) {
		viewController.dismissViewController(true, null);

	}

	@Override
	public void achievementViewControllerDidFinish(GPGAchievementController arg0) {
		viewController.dismissViewController(true, null);

	}

	@Override
	public void finishedWithAuth(GTMOAuth2Authentication auth, NSError error) {
		if (error == null) {
			System.out.println("logged in succesfully.");
			// after the google+ sign-in is done, we must continue the sign-in
// of 'games'.
			startGoogleGamesSignIn();
		} else {
			System.out.println("error during login: " + error.description());
			signedIn = false;
		}

	}

	private void startGoogleGamesSignIn() {
		final GPPSignIn s = GPPSignIn.sharedInstance();
		GPGManager m = GPGManager.sharedInstance();
		m.signIn();
		signedIn = true;
	}
}