package com.global.labs.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class MyWebService extends AsyncTask<Void, Void, Void> {
	Activity activity;
	String weburl, result = "x";
	boolean Sucess = true;

	localinterface<String, String> myInterface;
	String parms;

	
	public MyWebService(Activity activity, String weburl, String parms) {
		this.weburl = weburl;
		this.activity = activity;
		this.parms = parms;

	}

	public void Result(localinterface<String, String> myInterface) {
		this.myInterface = myInterface;
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

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (Sucess) {
			myInterface.Success(this.result);
		} else {
			myInterface.Error("Ex = " + this.result);
			Log.v(TAG, this.result);
		}
	}

	String TAG = "SIMU";

	public interface localinterface<E, R> {
		public void Success(E reslut);

		public void Error(R Error);
	}

}
