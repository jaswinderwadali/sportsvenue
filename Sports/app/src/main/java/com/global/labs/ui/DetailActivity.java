package com.global.labs.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.global.labs.adapters.PagerAdapter;
import com.global.labs.sports.R;

import java.util.Map;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailbar);
        toolbar.setTitle("");
        TextView header = (TextView) toolbar.findViewById(R.id.title);
        header.setText("Detail Activity");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pagersetup();
        dotss();
        get_Ids();
    }


    void get_Ids() {
        LinearLayout showmapll = (LinearLayout) findViewById(R.id.showmapll);
        showmapll.setOnClickListener(this);

    }

    void pagersetup() {
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(pagechange);
    }


    int dotsCount;
    TextView[] dots;

    void dotss() {
        LinearLayout dotslayout = (LinearLayout) findViewById(R.id.dotslayout);

        dotsCount = 4;
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(ContextCompat.getColor(this, R.color.standard));
            dotslayout.addView(dots[i]);
        }
        dots[0].setTextColor(ContextCompat.getColor(this, R.color.common_action_bar_splitter));


    }

    ViewPager.OnPageChangeListener pagechange = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setTextColor(ContextCompat.getColor(DetailActivity.this, R.color.standard));
            }
            dots[position].setTextColor(ContextCompat.getColor(DetailActivity.this, R.color.common_action_bar_splitter));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.showmapll:
                startActivity(new Intent(DetailActivity.this, MapsActivity.class).putExtras(getIntent()));
                break;
            default:
                break;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
