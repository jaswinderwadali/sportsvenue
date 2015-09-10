package com.global.labs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.facebook.AccessToken;
import com.global.labs.R;

public class Splash extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Delay();
    }


    void Delay() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (AccessToken.getCurrentAccessToken() != null) {
                    startActivity(new Intent(Splash.this, NavigationActivity.class));
                } else {
                    startActivity(new Intent(Splash.this, Login_with.class));
                }
                finish();


            }
        }, 1000);
    }
}
