package com.global.labs.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.global.labs.common.Constants;
import com.global.labs.common.JsonParsing;
import com.global.labs.sports.R;
import com.global.labs.utils.Internet;
import com.global.labs.utils.ResultBack;
import com.global.labs.utils.WebService;


public class AdvaceSearch extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custumactionbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinersetup();
        getids();

    }


    void getids() {
        findViewById(R.id.searchtv).setOnClickListener(this);
    }

    String[] cityarray = {"-- Select Location --", "Mg Road", "WhiteField", "Bannerghatta", "Mysore Road", "Hosur Road", "New Airport Road"};
    String[] Sportarray = {"-- Select Sports --", "Tennis", "Cricket", "Football", "Basketball", "Vollyball", "Hockey"};

    void spinersetup() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spiner_item, cityarray);
        Spinner spiner = (Spinner) findViewById(R.id.cityspiner);
        spiner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<String> adaptertwo = new ArrayAdapter<String>(this,
                R.layout.spiner_item, Sportarray);
        Spinner sportspiner = (Spinner) findViewById(R.id.sportsspiner);
        sportspiner.setAdapter(adaptertwo);
        adaptertwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spiner.setOnItemSelectedListener(itemseletc);
        sportspiner.setOnItemSelectedListener(itemseletc);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchtv:
                if (area != 0)
                    if (sports != 0)
                        if (Internet.getInstance().internetConnectivity(AdvaceSearch.this))
                            callwebservice();
                        else
                            Snackbar.make(findViewById(R.id.searchtv), "Please Check Internet", Snackbar.LENGTH_SHORT).show();
                    else
                        Snackbar.make(this.findViewById(R.id.searchtv), "Please Select Sports Type", Snackbar.LENGTH_SHORT).show();
                else
                    Snackbar.make(findViewById(R.id.searchtv), "Please Select Area", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }


    int sports = 0, area = 0;
    AdapterView.OnItemSelectedListener itemseletc = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            switch (parent.getId()) {
                case R.id.cityspiner:
                    area = position;
                    break;
                case R.id.sportsspiner:
                    sports = position;
                    break;
                default:
                    break;


            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    ProgressDialog dilog;

    void callwebservice() {
        {
            dilog = new ProgressDialog(this);
            dilog.setMessage("Searching....");
            dilog.setCancelable(false);
            dilog.show();
            WebService web = new WebService("sport=" + Sportarray[sports] + "&area=" + cityarray[area], this, Constants.URL + "/getsearchAdvanced");
            web.Result(new ResultBack() {
                @Override
                public void Result(String str, boolean status) {
                    dilog.dismiss();
                    if (status)
                        if (JsonParsing.HasData(str))
                            startActivity(new Intent(AdvaceSearch.this, ResultActivity.class).putExtra("DATA", str));
                        else
                            Snackbar.make(findViewById(R.id.searchtv), "Result Not Found..", Snackbar.LENGTH_SHORT).show();
                    else
                        Snackbar.make(findViewById(R.id.searchtv), "NetWork Error Or Server Error", Snackbar.LENGTH_SHORT).show();
                }
            });
            web.execute();
        }

    }


}
