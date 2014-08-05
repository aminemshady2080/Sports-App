package com.aminemApps.sportsapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpManager {

	public String getData(String uri) {

		BufferedReader reader = null;
		try {
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			StringBuilder sb = new StringBuilder();

			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			//Log.e("getData Exception msg ", e.getMessage());
			return null;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					return null;
				}
		}

	}

}
