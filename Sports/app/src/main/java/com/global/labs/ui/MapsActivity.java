package com.global.labs.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.global.labs.common.JsonParsing;
import com.global.labs.common.SeachModel;
import com.global.labs.sports.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custumbar);
        toolbar.setTitle("");
        TextView header = (TextView) toolbar.findViewById(R.id.title);
        header.setText("Map Location");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        if (getIntent().hasExtra("indusual")) {
            try {

                List<SeachModel> mDatalist = JsonParsing.SearchParsing(getIntent().getStringExtra("DATA"));
                for (SeachModel model : mDatalist) {

                    Float lat = Float.parseFloat(model.getLat());
                    Float mlong = Float.parseFloat(model.getMlong());
                    LatLng ltlg = new LatLng(lat, mlong);
                    mMap.addMarker(new MarkerOptions().position(ltlg).title(getIntent().getStringExtra("MARK")));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlg, 15));
//                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    mMap.setOnMarkerClickListener(this);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            String lati = getIntent().getStringExtra("Lat");
            System.out.println("" + lati);
            Float lat = Float.parseFloat(getIntent().getStringExtra("Lat"));
            Float mlong = Float.parseFloat(getIntent().getStringExtra("Long"));
            LatLng ltlg = new LatLng(lat, mlong);
            mMap.addMarker(new MarkerOptions().position(ltlg).title(getIntent().getStringExtra("MARK")));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlg, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            mMap.setOnMarkerClickListener(this);

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // startActivity(new Intent(MapsActivity.this, NavigationActivity.class));

        return false;
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
