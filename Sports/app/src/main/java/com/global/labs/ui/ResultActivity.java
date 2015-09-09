package com.global.labs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.global.labs.R;
import com.global.labs.adapters.ResultAdapter;
import com.global.labs.common.JsonParsing;
import com.global.labs.common.SeachModel;

import org.json.JSONException;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.resulttoolbar);
        TextView header = (TextView) toolbar.findViewById(R.id.title);
        header.setText(R.string.app_name);
        header.setText("Results");
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_result__add, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.showall) {
            startActivity(new Intent(this, MapsActivity.class).putExtra("indusual", false).putExtras(getIntent()));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
