package com.aminemApps.sportsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

public class IntroActivity extends Activity {
	ImageView bg;
	MediaPlayer bgsound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Aminem's Sports App");
		bg = new ImageView(getApplicationContext());
		bg.setImageResource(R.drawable.sportsbg);
		setContentView(bg);
		setTitle("Aminem Android Sports App");
		setTitleColor(Color.WHITE);
		bgsound = MediaPlayer.create(getApplicationContext(), R.raw.intro);
		bgsound.setVolume(.7f, .7f);
		bgsound.start();
		Thread t = new Thread() {
			public void run() {
				try {
					sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent in = new Intent(getApplicationContext(),
							FrontPage.class);
					startActivity(in);
				}
			}
		};
		t.start();
	}

	public void onPause() {
		super.onPause();
		bgsound.release();
		finish();
	}

}
