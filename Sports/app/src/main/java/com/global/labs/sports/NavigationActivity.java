package com.global.labs.sports;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v4.widget.DrawerLayout;

public class NavigationActivity extends AppCompatActivity {


    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout Drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
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
        //      toolbar.setNavigationIcon(R.drawable.ic_play_light);

        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        RecyclerView rv = (RecyclerView) findViewById(R.id.LeftDrawer);
        String[] str = {"Home", "About", "Blog", "Help & Feedback", "Contact Us"};
        Drawer_Adapter adapter = new Drawer_Adapter(str, this);
        rv.setAdapter(adapter);
        LinearLayoutManager mgr = new LinearLayoutManager(this);
        mgr.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mgr);
//        rv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        changefragment();
    }


    void changefragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }


}
