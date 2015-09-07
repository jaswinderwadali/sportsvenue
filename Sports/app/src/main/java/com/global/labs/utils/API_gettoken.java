package com.global.labs.utils;

import java.io.IOException;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.content.Context;
import android.os.AsyncTask;

@SuppressWarnings("rawtypes")
public class API_gettoken extends AsyncTask {

	Context context;

	public API_gettoken(Context context) {
		this.context = context;
	}

	Myinterface<String> responce;
	String regid;
	boolean success = true;

	public void getresponce(Myinterface<String> responce) {
		this.responce = responce;
	}

	@Override
	protected Object doInBackground(Object... params) {

		String PROJECT_NUMBER = "599502553549";
		GoogleCloudMessaging gcm;
		gcm = GoogleCloudMessaging.getInstance(context);
		try {
			regid = gcm.register(PROJECT_NUMBER);
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if (success) {
			responce.Success(regid);
		} else {
			responce.Fail("Error");
		}
	}

	public interface Myinterface<E> {
		public void Success(E Message);

		public void Fail(E Message);
	}

}
