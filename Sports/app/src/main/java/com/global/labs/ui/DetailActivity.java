package com.global.labs.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.global.labs.R;
import com.global.labs.adapters.PagerAdapter;
import com.global.labs.adapters.ReviewsAdapter;
import com.global.labs.common.Constants;
import com.global.labs.common.JsonParsing;
import com.global.labs.common.ReviewModel;
import com.global.labs.utils.Interface_Result;
import com.global.labs.utils.ResultBack;
import com.global.labs.utils.WebService;
import com.global.labs.utils.WebserviceGET;

import java.util.ArrayList;
import java.util.List;

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
        mLoadreviews();
    }


    void get_Ids() {
        LinearLayout showmapll = (LinearLayout) findViewById(R.id.showmapll);
        showmapll.setOnClickListener(this);
        TextView gameinfo = (TextView) findViewById(R.id.gameinfo);
        TextView adresstv = (TextView) findViewById(R.id.adresstv);
        TextView Contactno = (TextView) findViewById(R.id.Contactno);
//        findViewById(R.id.showallreviews).setOnClickListener(this);

        findViewById(R.id.reviewlay).setVisibility(View.INVISIBLE);
        findViewById(R.id.reviewprogresbar).setVisibility(View.VISIBLE);


        Bundle bundle = getIntent().getExtras();
        adresstv.setText(bundle.getString(Constants.ADDRESS));
        gameinfo.setText(bundle.getString(Constants.INFO));
//        Contactno.setText("ContactNo : " + bundle.getString(Constants.PHONENO));
        bundle.getString(Constants.GROUNDNAME);
        bundle.getString(Constants.AREA);
        bundle.getString(Constants.CITY);
        findViewById(R.id.ratelayout).setOnClickListener(this);
    }

    void pagersetup() {

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        if (getIntent().hasExtra(Constants.IMAGES)) {
            ArrayList<String> imagelist = getIntent().getStringArrayListExtra(Constants.IMAGES);
            if (imagelist != null) {
                if (imagelist.size() > 0) {
                    PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), getIntent().getStringArrayListExtra(Constants.IMAGES), DetailActivity.this);
                    pager.setAdapter(adapter);
                    pager.addOnPageChangeListener(pagechange);
                    dotsCount = imagelist.size();
                }

            }
        }
    }


    int dotsCount = 1;
    TextView[] dots;

    void dotss() {

        LinearLayout dotslayout = (LinearLayout) findViewById(R.id.dotslayout);


        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(getResources().getColor(R.color.standard));
            dotslayout.addView(dots[i]);
        }
        dots[0].setTextColor(getResources().getColor(R.color.common_action_bar_splitter));


    }

    ViewPager.OnPageChangeListener pagechange = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setTextColor(getResources().getColor(R.color.standard));
            }
            dots[position].setTextColor(getResources().getColor(R.color.common_action_bar_splitter));
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
            case R.id.showallreviews:
                mLoadreviews();
                break;
            case R.id.ratelayout:
                openratingpopup();
                break;
            default:
                break;

        }


    }


    private void mLoadreviews() {

        WebserviceGET getweb = new WebserviceGET(this, Constants.URL + "/api/ground/" + getIntent().getExtras().getString(Constants.ID));
        getweb.Result(new Interface_Result() {
            @Override
            public void Success(String str) {
                try {
                    findViewById(R.id.reviewlay).setVisibility(View.VISIBLE);
                    findViewById(R.id.reviewprogresbar).setVisibility(View.INVISIBLE);
                    List<ReviewModel>  reviews= new JsonParsing().mReviewList(str);
                    ReviewsAdapter adapter= new ReviewsAdapter(reviews);
                    ListView listView = (ListView) findViewById(R.id.reviewlistview);
                    listView.setAdapter(adapter);
                } catch (Exception e) {

                    e.printStackTrace();


                }
            }

            @Override
            public void Error(String message) {

                Snackbar.make(findViewById(R.id.textView), "Error", Snackbar.LENGTH_SHORT).show();
            }
        });
        getweb.execute();



    }

    private void mSendreview(String review, int rateing, String title) {
        WebService web = new WebService("groundId=" + getIntent().getExtras().getString(Constants.ID) + " &title=" + title + "&review=" + review + "&rating=" + rateing + "", DetailActivity.this, Constants.URL + "/createReview");
        web.Result(new ResultBack() {
            @Override
            public void Result(String str, boolean falg) {
                Log.v("", "" + str);
                Toast.makeText(DetailActivity.this, "" + str, Toast.LENGTH_SHORT).show();
            }
        });
        web.execute();
    }


    private void openratingpopup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.reviewlayout);
        dialog.setTitle("Rate And Review App");
        dialog.setCancelable(false);
        dialog.show();

        dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText titleet = (EditText) dialog.findViewById(R.id.reviewtitleet);
                EditText descet = (EditText) dialog.findViewById(R.id.reviewdecet);
                if (titleet.getText().toString().trim().length() > 0) {
                    TextView title = (TextView) findViewById(R.id.myreviewtitle);
                    title.setText(titleet.getText().toString().trim());
                    TextView dec = (TextView) findViewById(R.id.myreviewdesc);
                    dec.setText(descet.getText().toString().trim());

                    mSendreview(descet.getText().toString().trim(), rating, titleet.getText().toString().trim());
                    dialog.dismiss();
                } else {
                    Toast.makeText(DetailActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.findViewById(R.id.cancel_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.onestar).setOnClickListener(onclick);
        dialog.findViewById(R.id.twostar).setOnClickListener(onclick);
        dialog.findViewById(R.id.threestar).setOnClickListener(onclick);
        dialog.findViewById(R.id.fourstar).setOnClickListener(onclick);
        dialog.findViewById(R.id.fivestar).setOnClickListener(onclick);


    }

    private int rating = 0;
    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.onestar:
                    rating = 1;
                    mFillStar((ImageView) view);
                    break;
                case R.id.twostar:
                    rating = 2;
                    mFillStar((ImageView) view);

                    break;
                case R.id.threestar:
                    rating = 3;
                    mFillStar((ImageView) view);

                    break;
                case R.id.fourstar:
                    rating = 4;
                    mFillStar((ImageView) view);

                    break;
                case R.id.fivestar:
                    rating = 5;
                    mFillStar((ImageView) view);

                    break;
                default:
                    rating = 0;

                    break;


            }


        }
    };

    private void mFillStar(ImageView view) {
        View parent = (View) view.getParent();
        ImageView one = (ImageView) parent.findViewById(R.id.onestar);
        ImageView two = (ImageView) parent.findViewById(R.id.twostar);
        ImageView three = (ImageView) parent.findViewById(R.id.threestar);
        ImageView four = (ImageView) parent.findViewById(R.id.fourstar);
        ImageView five = (ImageView) parent.findViewById(R.id.fivestar);
        one.clearColorFilter();
        two.clearColorFilter();
        three.clearColorFilter();
        four.clearColorFilter();
        five.clearColorFilter();

        int color = Color.parseColor("#AE6118");
        switch (view.getId()) {
            case R.id.onestar:
                one.setColorFilter(color);
                break;
            case R.id.twostar:
                one.setColorFilter(color);
                two.setColorFilter(color);
                break;
            case R.id.threestar:
                one.setColorFilter(color);
                two.setColorFilter(color);
                three.setColorFilter(color);
                break;
            case R.id.fourstar:
                one.setColorFilter(color);
                two.setColorFilter(color);
                three.setColorFilter(color);
                four.setColorFilter(color);
                break;
            case R.id.fivestar:
                one.setColorFilter(color);
                two.setColorFilter(color);
                three.setColorFilter(color);
                four.setColorFilter(color);
                five.setColorFilter(color);
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
