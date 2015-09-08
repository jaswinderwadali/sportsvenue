package com.global.labs.utils;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class BackGroundWebService extends AsyncTask<Void, Void, Void> {
	Activity activity;
	String weburl, result = "x";
	boolean Sucess = true;
	String parms;

	public BackGroundWebService(String weburl, String parms) {
		this.weburl = weburl;
		this.parms = parms;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			byte[] postData = parms.getBytes(Charset.forName("UTF-8"));
			int postDataLength = postData.length;
			URL url = new URL(weburl);
			HttpURLConnection cox = (HttpURLConnection) url.openConnection();
			cox.setDoOutput(true);
			cox.setDoInput(true);
			cox.setInstanceFollowRedirects(false);
			cox.setRequestMethod("POST");
			cox.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			cox.setRequestProperty("charset", "utf-8");
			cox.setRequestProperty("Content-Length",
					Integer.toString(postDataLength));
			cox.setUseCaches(false);
			OutputStreamWriter writer = new OutputStreamWriter(
					cox.getOutputStream());
			writer.write(parms);
			writer.flush();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					cox.getInputStream()));
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
			writer.close();
			reader.close();
		} catch (Exception e) {
			result = e.toString();
			Sucess = false;
			e.printStackTrace();
		}
		return null;
	}

	String TAG = "SIMU";

}
