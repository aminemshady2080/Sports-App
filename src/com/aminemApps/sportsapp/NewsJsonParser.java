package com.aminemApps.sportsapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class NewsJsonParser {

	// public NewsJsonParser(Context context) {
	// super(context);
	// // TODO Auto-generated constructor stub
	// new newsTask().execute();
	// }

	String teamId = "";
	static String APIURI = "http://api.espn.com/v1/sports/soccer/eng.1/athletes/teams/";
	private static final String APIKEY = "?apikey=pdhmxy828mdagnwvhecmd4jy";
	String myfulluri = "http://api.espn.com/v1/sports/soccer/news/headlines?apikey=pdhmxy828mdagnwvhecmd4jy";

	TextView tv;
	ListView lv;
	Context c;

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.firstabs);
	//
	// }
	public NewsJsonParser() {
		finalData();
		c=FrontPage.cc;
	}

	List<String> finalData() {
		newsTask n = new newsTask();
		n.execute();
		return data;
	}

	public static List<String> data = new ArrayList<String>();
	List<String> heads = new ArrayList<String>();
	List<String> dates = new ArrayList<String>();
	List<String> descrpt = new ArrayList<String>();

	class newsTask extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {

			// parsePlayers();
			try {
				JsonArray allheads = new JsonParser().parse(newsJsonData())
						.getAsJsonObject().get("headlines").getAsJsonArray();
				for (JsonElement news : allheads) {
					String newsHead = news.getAsJsonObject().get("headline")
							.getAsString();

					String newsdate = news.getAsJsonObject()
							.get("lastModified").getAsString();

					JsonArray vids = news.getAsJsonObject().get("video")
							.getAsJsonArray();
					String viDescrpt = null;
					for (JsonElement vid : vids) {
						viDescrpt = vid.getAsJsonObject().get("description")
								.getAsString();

					}
					heads.add(newsHead);
					dates.add(newsdate);
					descrpt.add(viDescrpt);
				}
				StringBuffer s = null;
				for (int i = 0; i < heads.size(); i++) {
					s = new StringBuffer();
					int ii = i + 1;
					s.append(" Top News No:" + ii + "\n"
							+ " ============ \n\n " + heads.get(i));
					s.append("\n News Time : \t");
					s.append(dates.get(i).replaceAll("[A-Z]", " "));
					s.append("\n \n");
					s.append(" " + descrpt.get(i) + " \n \n");
					System.out.println("string bufer data : " + s.toString());
					data.add(s.toString());
				}
				System.out.println("parsed data : " + data);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data;

		}

		@Override
		protected void onPostExecute(List<String> result) {

			ArrayAdapter<String> adp = new ArrayAdapter<String>(c,
					android.R.layout.simple_list_item_1, result);
			FrontPage.lv.setAdapter(adp);

		}

	}

	String newsJsonData() throws Exception {
		BufferedReader reader = null;
		StringBuffer sb = null;
		try {
			URL uri = new URL(myfulluri);
			HttpURLConnection con = (HttpURLConnection) uri.openConnection();

			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			sb = new StringBuffer();
			int line;
			final char[] lines = new char[1024];
			while ((line = reader.read(lines)) != -1) {
				sb.append(lines, 0, line);
			}
			Log.i("retned data hhh= ", sb.toString());
			return sb.toString();
		} finally {
			if (reader != null) {

				reader.close();

			}

		}
	}

	String fullUri() {
		Intent intent = new Intent();
		teamId = intent.getStringExtra("sentId");
		return APIURI + teamId + APIKEY;
	}

}