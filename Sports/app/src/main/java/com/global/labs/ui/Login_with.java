package com.global.labs.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.global.labs.R;
import com.global.labs.common.Constants;
import com.global.labs.utils.ResultBack;
import com.global.labs.utils.WebService;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Login_with extends AppCompatActivity implements View.OnClickListener {

    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with);
        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(new Intent(this, NavigationActivity.class));
            finish();
        }
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        String email = user.optString("email");
                        String name = user.optString("name");
                        String id = user.optString("id");
                        if (email != null) {
                            startActivity(new Intent(Login_with.this, NavigationActivity.class));
                            finish();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        getids();

        SessionMaintain();
    }


    private void getids() {

        findViewById(R.id.skip).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.skip:
                startActivity(new Intent(Login_with.this, NavigationActivity.class));
                finish();
                break;
            default:
                break;
        }

    }


    @Override
    protected void onPostResume() {
       // manager();
        super.onPostResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    void getkeyhash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    this.getPackageName(),
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            System.out.println("" + e);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("" + e);
        }
    }






    void manager(){
     WebService web = new WebService("", Login_with.this, Constants.URL + "/user/55f299028abc50d0703569ce");
        web.Result(new ResultBack() {
            @Override
            public void Result(String str, boolean falg) {
                Log.v("", "" + str);
                Toast.makeText(Login_with.this,""+str,Toast.LENGTH_SHORT).show();
            }
        });
        web.execute();
    }
    void SessionMaintain() {

        WebService web = new WebService("email=jaswinder.wadali@mantralabsglobal.com&password=RFdXk", this, Constants.URL + "/api/testlogin");
        web.Result(new ResultBack() {
            @Override
            public void Result(String str, boolean falg) {

                Log.v("", "" + str);


                WebService web = new WebService("", Login_with.this, Constants.URL + "/user/55f299028abc50d0703569ce");
                web.Result(new ResultBack() {
                    @Override
                    public void Result(String str, boolean falg) {

                            Log.v("", "" + str);

                    }
                });
                web.execute();


            }
        });
        web.execute();


    }


}
