package com.global.labs.ui;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.global.labs.common.GMapV2Direction;
import com.global.labs.common.JsonParsing;
import com.global.labs.common.SeachModel;
import com.global.labs.sports.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.w3c.dom.Document;

import java.util.ArrayList;
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

    List<SeachModel> mDatalist;
    int position = 0;

    private void setUpMap() {
        if (getIntent().hasExtra("indusual")) {
            try {

                mDatalist = JsonParsing.SearchParsing(getIntent().getStringExtra("DATA"));
                for (SeachModel model : mDatalist) {

                    Float lat = Float.parseFloat(model.getLat());
                    Float mlong = Float.parseFloat(model.getMlong());
                    LatLng ltlg = new LatLng(lat, mlong);
                    mMap.addMarker(new MarkerOptions().position(ltlg).title(model.getGroundName()).snippet("" + position));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlg, 15));
//                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    mMap.setOnMarkerClickListener(this);
                    position++;
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
            mMap.setMyLocationEnabled(true);
            mMap.addMarker(new MarkerOptions().position(ltlg).title(getIntent().getStringExtra("MARK")).snippet("" + position));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlg, 15));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            mMap.setOnMarkerClickListener(this);
            mMap.setOnMyLocationChangeListener(myLocationChangeListener);


        }
    }


    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            new Drawpath(location).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    };


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (getIntent().hasExtra("indusual")) {
            int poisition = Integer.parseInt(marker.getSnippet());
            startActivity(new Intent(this, DetailActivity.class).putExtra("Lat", mDatalist.get(poisition).getLat()).putExtra("Long", mDatalist.get(poisition).getMlong()).putExtra("MARK", mDatalist.get(poisition).getGroundName()));
        } else {
        }
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


    class Drawpath extends AsyncTask<String, String, PolylineOptions> {
        Location location;

        Drawpath(Location location) {
            this.location = location;
        }


        @Override
        protected PolylineOptions doInBackground(String... strings) {

            GMapV2Direction md = new GMapV2Direction();
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            String lati = getIntent().getStringExtra("Lat");
            System.out.println("" + lati);
            Float lat = Float.parseFloat(getIntent().getStringExtra("Lat"));
            Float mlong = Float.parseFloat(getIntent().getStringExtra("Long"));
            LatLng ltlg = new LatLng(lat, mlong);

            Document doc = md.getDocument(loc, ltlg, GMapV2Direction.MODE_DRIVING);

            ArrayList<LatLng> directionPoint = md.getDirection(doc);
            PolylineOptions rectLine = new PolylineOptions().width(3).color(getResources().getColor(R.color.standard));

            for (int i = 0; i < directionPoint.size(); i++) {
                rectLine.add(directionPoint.get(i));
            }
            return rectLine;
        }

        @Override
        protected void onPostExecute(PolylineOptions s) {
            super.onPostExecute(s);
            mMap.addPolyline(s);
        }

    }

}
