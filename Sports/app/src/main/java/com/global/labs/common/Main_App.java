package com.global.labs.common;

import android.app.Application;

import com.facebook.FacebookSdk;

import java.net.CookieHandler;
import java.net.CookieManager;

/**
 * Created by Mantra on 9/9/2015.
 */
public class Main_App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }


}
