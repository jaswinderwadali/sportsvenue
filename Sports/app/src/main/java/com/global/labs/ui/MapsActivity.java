package com.global.labs.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.global.labs.sports.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        String lati=getIntent().getStringExtra("Lat");
        System.out.println(""+lati);
        Float lat = Float.parseFloat(getIntent().getStringExtra("Lat"));
        Float mlong = Float.parseFloat(getIntent().getStringExtra("Long"));
        LatLng ltlg = new LatLng(lat, mlong);
        mMap.addMarker(new MarkerOptions().position(ltlg).title(getIntent().getStringExtra("MARK")));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlg, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
       // startActivity(new Intent(MapsActivity.this, NavigationActivity.class));

        return false;
    }
}
