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
//        ImageView imageView = (ImageView) findViewById(R.id.loaderimage);
//        Picasso.with(this)
//                .load("http://blog.jimdo.com/wp-content/uploads/2014/01/tree-247122.jpg")
//                .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                .into(imageView);
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
