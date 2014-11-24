package com.jumpbuttonstudios.totemgame.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jumpbuttonstudios.totemgame.android.R;


import com.boom.videoboom.OfferlistActivity;
import com.boom.videoboom.VideoActivityNYT;
import com.boom.videoboom.VideoAds;

public class ButtonActivity extends Activity {

	TextView tvName;
	Button btnName;
	Button fetch;
	TextView txtfetch;
	Button btnNonYT;
	Button testBT;

	Button listv;
	private int num = 0;

	private final String Boom_GUID = "ad73bf22-a8c7-49fa-a217-43c4ecd32704";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.buttonscreen_main);

		setListener();
		// set boomguid
		VideoAds.setID(Boom_GUID);

		// call VideoAds.start(activity) to initialize
		VideoAds.start(this);

		// Button to Fetch and Display Video
		tvName = (TextView) findViewById(R.id.tvName);
		btnName = (Button) findViewById(R.id.btnWatchVideo);
		txtfetch = (TextView) findViewById(R.id.txtfetch);
		btnNonYT = (Button) findViewById(R.id.btnNonYT);
		testBT = (Button) findViewById(R.id.testBT);

		listv = (Button) findViewById(R.id.btnVideoList);

	}

	public void Test(View view) {

		if (!isNetworkAvailable()) {
			Toast.makeText(ButtonActivity.this, getResources().getString(R.string.chk_connection),
					Toast.LENGTH_SHORT).show();

			return;
		}

		Intent intent = new Intent(ButtonActivity.this, OfferlistActivity.class);
		intent.putExtra("guid", Boom_GUID);

		startActivity(intent);
		// TODO Auto-generated method stub
	}

	public void NonYT(View view) {

		if (!isNetworkAvailable()) {
			Toast.makeText(ButtonActivity.this, getResources().getString(R.string.chk_connection),
					Toast.LENGTH_SHORT).show();

			return;
		}

		VideoAds.Fetch(this);
		if (VideoAds.isAvaliable()) {
			Intent intent = new Intent(this, VideoActivityNYT.class);
			intent.putExtra("Video", VideoAds.getvideocontent());
			intent.putExtra("Videoindex", "-1");
			startActivityForResult(intent, 1);
		}
	}

	public void Preroll(View view) {

		if (!isNetworkAvailable()) {
			txtfetch.setText(getResources().getString(R.string.chk_connection));
			Toast.makeText(ButtonActivity.this, getResources().getString(R.string.chk_connection),
					Toast.LENGTH_SHORT).show();

			return;
		}

		// Fetch to get Campaign information
		VideoAds.Fetch(this);
		// Check Campaign available or not.
		if (VideoAds.isAvaliable()) {
			// campaign is available
			VideoAds.Display_pre_roll(this);
		}
	}

	// Check Campaign available or not.
	public void FetchVideo(View view) {

		if (!isNetworkAvailable()) {
			txtfetch.setText(getResources().getString(R.string.chk_connection));
			Toast.makeText(ButtonActivity.this, getResources().getString(R.string.chk_connection),
					Toast.LENGTH_SHORT).show();

			return;
		}

		txtfetch.setText("");
		tvName.setText("Video status");
		// Fetch to get Campaign information
		VideoAds.Fetch(this);
		// Check Campaign available or not.
		if (VideoAds.isAvaliable()) {
			// campaign is available
			txtfetch.setText("Video is avaliable");
		} else
			txtfetch.setText("No More Video is avaliable");
	}

	// Display Campaign
	public void WatchVideo(View view) {

		if (!isNetworkAvailable()) {
			txtfetch.setText(getResources().getString(R.string.chk_connection));
			Toast.makeText(ButtonActivity.this, getResources().getString(R.string.chk_connection),
					Toast.LENGTH_SHORT).show();

			return;
		}

		txtfetch.setText("");
		VideoAds.Display(this);

	}

	// set Listener for Reward
	private void setListener() {
		VideoAds.setListener(new VideoAds.OnResultListener() {

			@Override
			public void onComplete() {
				// TODO Auto-generated method st

				Toast.makeText(ButtonActivity.this, "Congrats!! You got additional points",
						Toast.LENGTH_LONG).show();

			}

		});
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}

