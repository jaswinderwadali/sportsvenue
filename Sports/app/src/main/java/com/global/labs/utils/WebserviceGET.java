package com.global.labs.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Mantra on 9/9/2015.
 */
public class WebserviceGET extends AsyncTask<Void, Void, String> {


    Context ctx;
    String mURL;

    public WebserviceGET(Context ctx, String mURL) {
        this.mURL = mURL;
        this.ctx = ctx;
    }

    Interface_Result inter;

    public void Result(Interface_Result inter) {
        this.inter = inter;
    }

    @Override
    protected String doInBackground(Void... strings) {
        try {

            HttpClient client = new DefaultHttpClient();
            URI website = new URI(mURL);
            HttpGet request = new HttpGet();
            request.setURI(website);
            HttpResponse response = client.execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s != null)
            inter.Success(s);
        else
            inter.Error(s);

    }
}
