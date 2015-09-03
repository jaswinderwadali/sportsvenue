package com.global.labs.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.global.labs.common.JsonParsing;
import com.global.labs.common.SeachModel;
import com.global.labs.sports.R;

import org.json.JSONException;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.resulttoolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Setupadapter();
    }

    private void Setupadapter() {

        try {
            List<SeachModel> mDatalist = JsonParsing.SearchParsing(getIntent().getStringExtra("DATA"));
            RecyclerView rv = (RecyclerView) findViewById(R.id.LeftDrawer);
            ResultAdapter adapter = new ResultAdapter(mDatalist, this);
            rv.setAdapter(adapter);
            LinearLayoutManager mgr = new LinearLayoutManager(this);
            mgr.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(mgr);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
