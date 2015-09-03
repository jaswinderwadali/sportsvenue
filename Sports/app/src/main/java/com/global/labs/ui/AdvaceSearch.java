package com.global.labs.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.global.labs.sports.R;


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

    void spinersetup() {
        String[] cityarray = {"Location", "Bangluru", "Mumbai", "Delhi", "Pune"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spiner_item, cityarray);
        Spinner spiner = (Spinner) findViewById(R.id.cityspiner);
        spiner.setAdapter(adapter);


        String[] Sportarray = {"Sports", "Tennis", "Cricket", "Football", "Basketball", "Vollyball", "Hockey"};
        ArrayAdapter<String> adaptertwo = new ArrayAdapter<String>(this,
                R.layout.spiner_item, Sportarray);
        Spinner sportspiner = (Spinner) findViewById(R.id.sportsspiner);
        sportspiner.setAdapter(adaptertwo);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchtv:
                startActivity(new Intent(this, MapsActivity.class));
            default:
                Toast.makeText(this, "Testing"
                        , Toast.LENGTH_SHORT).show();

        }
    }

}
