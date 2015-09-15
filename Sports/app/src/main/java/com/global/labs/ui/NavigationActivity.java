package com.global.labs.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.global.labs.R;
import com.global.labs.adapters.Drawer_Adapter;
import com.global.labs.common.Constants;
import com.global.labs.common.DatabaseHelper;
import com.global.labs.common.JsonParsing;
import com.global.labs.common.MyClass;
import com.global.labs.utils.Interface_Result;
import com.global.labs.utils.WebserviceGET;

import de.greenrobot.event.EventBus;

public class NavigationActivity extends AppCompatActivity {


    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout Drawer;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    boolean doubleBackToExitPressedOnce = false;
    public static boolean resultnotfound = false;

    @Override
    public void onBackPressed() {
        if (resultnotfound) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        TextView header = (TextView) toolbar.findViewById(R.id.title);
        header.setText(R.string.app_name);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Drawer.isDrawerOpen(Gravity.LEFT))
//                    Drawer.closeDrawer(Gravity.LEFT);
//                else
//                    Drawer.openDrawer(Gravity.LEFT);
//
//
//            }
//        });
//        mDrawerToggle.setDrawerIndicatorEnabled(false);
//        toolbar.setNavigationIcon(R.drawable.ic_play_light);

        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        EventBus.getDefault().register(this);
        RecyclerView rv = (RecyclerView) findViewById(R.id.LeftDrawer);
        String[] str = {"Home", "About", "Help & Feedback", "Contact Us"};
        int[] ids = {R.drawable.homeicon, R.drawable.about, R.drawable.help_feedback, R.drawable.contactus};
        Drawer_Adapter adapter = new Drawer_Adapter(str, this, ids, getSupportFragmentManager(), Drawer);
        rv.setAdapter(adapter);
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mgr);
        if (!Constants.CHECK) {
            searchcall();
            Constants.CHECK = true;
        } else {
            changefragment();
        }


    }


    ProgressDialog dilog;

    private void searchcall() {
        dilog = new ProgressDialog(this);
        dilog.setMessage("Please Wait....");
        dilog.setCancelable(false);
        dilog.show();
        WebserviceGET getweb = new WebserviceGET(this, Constants.URL + "/api/fetchCityDetails");
        getweb.Result(new Interface_Result() {
            @Override
            public void Success(String str) {
                dilog.dismiss();
                try {
                    DatabaseHelper.getInstance(NavigationActivity.this).InsertMainTable(new JsonParsing().Maindata(str));
                    changefragment();
                    Constants.CHECK = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Constants.CHECK = false;
                    popup();
                }
            }

            @Override
            public void Error(String message) {
                dilog.dismiss();
                Constants.CHECK = false;
                Snackbar.make(findViewById(R.id.textView), "Error", Snackbar.LENGTH_SHORT).show();
                popup();
            }
        });
        getweb.execute();

    }


    void changefragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }


    void popup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                NavigationActivity.this);

        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("Network Problem !");

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                searchcall();
            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();

            }
        });

        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();
    }

    public void onEvent(MyClass e) {
        Toast.makeText(NavigationActivity.this, e.getStr(), Toast.LENGTH_LONG).show();
    }





}
