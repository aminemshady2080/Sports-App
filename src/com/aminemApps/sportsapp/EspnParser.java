package com.aminemApps.sportsapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aminemApps.sportsapp.models.Team;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EspnParser extends ListActivity {

	private static final String APIKEY = "pdhmxy828mdagnwvhecmd4jy";
	private static final String APIURL1 = "http://api.espn.com/v1/sports/soccer/";
	static String APIURL2;
	static String APIURL3 = "/teams?apikey=";
	static String urlnews = "http://api.espn.com/v1/sports/soccer/eng.1/news/headlines&apikey=pdhmxy828mdagnwvhecmd4jy";
	static String theTeam;
	List<Team> allTeams = new ArrayList<Team>();
	public static String urlink;

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setTitle(FrontPage.lgname+"Teams @ Aminem's Sports App");
		new mytask().execute();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		theTeam = l.getItemAtPosition(position).toString();
		String teamid = "";
		Toast.makeText(getApplicationContext(), "team is " + theTeam,
				Toast.LENGTH_SHORT).show();
		for (int i = 0; i < allTeams.size(); i++) {
			if (theTeam.equals(allTeams.get(i).getName())) {
				teamid = allTeams.get(i).getId();
			}
		}
		urlink = teamUrlink(theTeam, teamid);
		System.out.println("link to send is: " + urlink);
		Team t = new Team();
		t.setLink(urlink);
		try {
			Class<?> targetClass = Class
					.forName("com.aminemApps.sportsapp.Team_Front_page_activity");
			Intent intent = new Intent(getApplicationContext(), targetClass);

			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class mytask extends AsyncTask<Void, Void, List<String>> {
		List<String> dataList = new ArrayList<String>();

		@Override
		protected List<String> doInBackground(Void... params) {
			dataList = EspnData();
			return dataList;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			try {
				setListAdapter(new ArrayAdapter<>(getApplicationContext(),
						R.layout.onestory, result));
			} catch (Exception e) {
				Toast.makeText(
						getApplicationContext(),
						"Could not Make List of teams \n caused by "
								+ e.getMessage(), Toast.LENGTH_LONG).show();
			}

		}
	}

	public List<String> EspnData() {
		List<String> dataList = new ArrayList<String>();
		// for (int offset = 0; offset < 20; offset ++) {
		try {
			final JsonArray athletes = getAthletesJsonArray(0);
			for (final JsonElement athlete : athletes) {
				Team t = new Team();

				t.setId(athlete.getAsJsonObject().get("id").getAsString());
				t.setName(athlete.getAsJsonObject().get("name").getAsString());

				allTeams.add(t);
				System.out.println("team info " + t.toString());
				dataList.add(t.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
		return dataList;

	}

	private String teamUrlink(String teamname, String tId) {

		String name = teamname.replace(" ", "-");
		String link = "http://www.espnfc.com/club/" + name + "/" + tId
				+ "/squad?ex_cid=" + APIKEY;

		return link;

	}

	private static JsonArray getAthletesJsonArray(final int offset)
			throws Exception {
		final String json = readUrl(getUrl(offset));
		final JsonArray sports = getSportsJsonArray(json);
		final JsonElement league = sports.get(0);
		return league.getAsJsonObject().get("leagues").getAsJsonArray().get(0)
				.getAsJsonObject().get("teams").getAsJsonArray();
	}

	private static JsonArray getSportsJsonArray(final String json) {
		final JsonArray sports = new JsonParser().parse(json).getAsJsonObject()
				.get("sports").getAsJsonArray();
		return sports;
	}

	private static String getUrl(final int offset) {
		APIURL2 = FrontPage.leagueId;
		return APIURL1 + APIURL2 + APIURL3 + APIKEY + "&offset=" + offset;

	}

	private static String readUrl(final String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			final URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			final StringBuffer buffer = new StringBuffer();
			int read;
			final char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1) {
				buffer.append(chars, 0, read);
			}
			System.out.println("json data not formated" + buffer.toString());
			return buffer.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

}
