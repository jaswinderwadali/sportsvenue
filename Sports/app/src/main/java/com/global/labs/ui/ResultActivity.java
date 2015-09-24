package com.global.labs.ui;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.global.labs.R;
import com.global.labs.adapters.ResultAdapter;
import com.global.labs.common.JsonParsing;
import com.global.labs.common.SeachModel;

import org.json.JSONException;

import java.util.List;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        TextView header = (TextView) toolbar.findViewById(R.id.title);
        header.setText(R.string.app_name);
        header.setText("Results");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragmenttwo()).commit();
        findViewById(R.id.toggle).setOnClickListener(this);
//        Setupadapter();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag().equals(false)) {
            view.setTag(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Fragmenttwo()).commit();
        } else {
            view.setTag(false);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Map_Fragment()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        getMenuInflater().inflate(R.menu.menu_result__add, menu);

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



    class Fragmenttwo extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_result, container, false);
            Setupadapter(view);
            return view;
        }

        private void Setupadapter(View view) {

            try {
                List<SeachModel> mDatalist = JsonParsing.SearchParsing(getIntent().getStringExtra("DATA"));

                if (getIntent().hasExtra("lat"))
                    mDatalist = Sorting(mDatalist);

                RecyclerView rv = (RecyclerView) view.findViewById(R.id.LeftDrawer);
                ResultAdapter adapter = new ResultAdapter(mDatalist, getActivity());
                rv.setAdapter(adapter);
                LinearLayoutManager mgr = new LinearLayoutManager(getActivity());
                mgr.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(mgr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private List<SeachModel> Sorting(List<SeachModel> mdatalist) {

            double mylat = getIntent().getDoubleExtra("lat", 0.0);
            double mylong = getIntent().getDoubleExtra("mlong", 0.0);
            Location mylocation = new Location("Mylocation");
            mylocation.setLongitude(mylong);
            mylocation.setLatitude(mylat);

            for (int i = 0; mdatalist.size() > i; i++) {
                double lat = Double.parseDouble(mdatalist.get(i).getLat());
                double mlong = Double.parseDouble(mdatalist.get(i).getMlong());
                Location loc = new Location("Location");
                loc.setLatitude(lat);
                loc.setLongitude(mlong);
                double distance = mylocation.distanceTo(loc) / 1000;
                SeachModel model = mdatalist.get(i);
                model.setDistance(distance);
                mdatalist.set(i, model);
            }

            return mdatalist;
        }
    }
}
