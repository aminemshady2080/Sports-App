package com.aminemApps.sportsapp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Team_Front_page_activity extends Activity {

	WebView view;
	String theurl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.front_page_layout);
		
		view = (WebView) findViewById(R.id.teamsweb);

		theurl = EspnParser.urlink;
		view.loadUrl(theurl);
		view.getSettings().setLoadWithOverviewMode(true);
		view.getSettings().setBuiltInZoomControls(true);
		view.setWebViewClient(new viewClient());
		String t=EspnParser.theTeam;
		setTitle(t+" @ Aminem's Sports App ");
	}

	class viewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

	}
}
