package com.global.labs.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by deepa on 9/1/2015.
 */
public class WebService extends AsyncTask<Void, Void, String> {

    String parms;
    Context ctx;
    String mURL;

    public WebService(String parms, Context ctx, String mURL) {
        this.mURL = mURL;
        this.parms = parms;
        this.ctx = ctx;
    }

    ResultBack callback;

    public void Result(ResultBack callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        callback.Result(str, falg);

    }

    boolean falg = true;

    @Override
    protected String doInBackground(Void... params) {

        try {
            byte[] postData = parms.getBytes(Charset.forName("UTF-8"));
            int postDataLength = postData.length;
            URL url = new URL(mURL);
            HttpURLConnection cox = (HttpURLConnection) url.openConnection();
            cox.setDoOutput(true);
            cox.setDoInput(true);
            cox.setConnectTimeout(5000);
            cox.setInstanceFollowRedirects(false);
            cox.setRequestMethod("POST");
            cox.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            cox.setRequestProperty("charset", "utf-8");
            cox.setRequestProperty("Content-Length",
                    Integer.toString(postDataLength));
            cox.setUseCaches(true);
            cox.getUseCaches();
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
            writer.close();
            reader.close();
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            falg = false;
            return e.toString();
        }
    }
}


