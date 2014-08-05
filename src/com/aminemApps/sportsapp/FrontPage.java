package com.aminemApps.sportsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class FrontPage extends Activity implements View.OnClickListener {

	TabHost host;

	Button plBut, eligBut, bligBut, itBut, frBut;
	static String leagueId;
	String[] allILeaguesds = { "eng.1", "esp.1", "ger.1", "ita.1", "fra.1" };
	ListView lview;
	ImageView bg;
	static TextView tvnews;
	
	static String lgname=null;
	static ListView lv;
	static Context cc;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("Aminem's Sports App");

		setContentView(R.layout.firstabs);
		cc=getApplicationContext();
		host = (TabHost) findViewById(R.id.tabHost1);
		host.setup();
		TabSpec specs = host.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Teams & Players");
		host.addTab(specs);
		specs = host.newTabSpec("tag2");
		// specs.setContent(R.id.tab2);
		specs.setIndicator("News");
		// /Intent inte = intentfortab2();
		lv=(ListView) findViewById(R.id.newslist);
		
		specs.setContent(R.id.newslist);
		new NewsJsonParser();
		host.addTab(specs);
		//tvnews = (TextView) findViewById(R.id.tvnews);
		
		

		createAllButtons();

	}

	private void createAllButtons() {
		plBut = (Button) findViewById(R.id.plbut);
		eligBut = (Button) findViewById(R.id.espbut);
		bligBut = (Button) findViewById(R.id.bunbut);
		itBut = (Button) findViewById(R.id.itabut);
		frBut = (Button) findViewById(R.id.frbut);

		plBut.setOnClickListener(this);
		eligBut.setOnClickListener(this);
		bligBut.setOnClickListener(this);
		itBut.setOnClickListener(this);
		frBut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Class<?> targetClass = null;
		try {
			targetClass = Class.forName("com.aminemApps.sportsapp.EspnParser");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Intent intent = new Intent(getApplicationContext(), targetClass);
		switch (v.getId()) {
		case R.id.plbut:
			leagueId = "eng.1";
			lgname="Premier League ";
			startActivity(intent);
			break;
		case R.id.espbut:
			leagueId = "esp.1";
			lgname="La Liga ";
			startActivity(intent);
			break;

		case R.id.bunbut:
			leagueId = "ger.1";
			lgname="Bundes Liga ";
			startActivity(intent);
			break;
		case R.id.itabut:
			leagueId = "ita.1";
			lgname="Italia Serie A ";
			startActivity(intent);
			break;
		case R.id.frbut:
			leagueId = "fra.1";
			lgname="France Ligue 1 ";
			startActivity(intent);
			break;
		}

	}

}