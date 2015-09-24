package com.global.labs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.global.labs.R;
import com.global.labs.common.Constants;
import com.global.labs.common.JsonParsing;
import com.global.labs.common.SeachModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mantra on 9/24/2015.
 */
public class Map_Fragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapfragment, container, false);
        setUpMapIfNeeded(view);
        return view;
    }

    private void setUpMapIfNeeded(View view) {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
                mMap.setMyLocationEnabled(true);

            }
        }
    }


    private List<SeachModel> mDatalist;

    private void setUpMap() {
        int position = 0;

        try {
            mDatalist = JsonParsing.SearchParsing(getActivity().getIntent().getStringExtra("DATA"));
            LatLngBounds.Builder b = new LatLngBounds.Builder();
            for (SeachModel model : mDatalist) {

                Float lat = Float.parseFloat(model.getLat());
                Float mlong = Float.parseFloat(model.getMlong());
                LatLng ltlg = new LatLng(lat, mlong);
                MarkerOptions marker = new MarkerOptions().position(ltlg);
                mMap.addMarker(marker.title(model.getGroundName()).snippet("" + position));
                mMap.setOnMarkerClickListener(this);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlg, 15));
                b.include(marker.getPosition());
//                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
//                        mMap.setOnMarkerClickListener(this);
                position++;
            }
            LatLngBounds bounds = b.build();
            //Change the padding as per needed
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 45, 45, 5);
            mMap.animateCamera(cu, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    CameraUpdate zout = CameraUpdateFactory.zoomBy(3);
                    mMap.animateCamera(zout);
                }

                @Override
                public void onCancel() {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


//            } else {
//
//                String lati = getIntent().getStringExtra("Lat");
//                System.out.println("" + lati);
//                Float lat = Float.parseFloat(getIntent().getStringExtra("Lat"));
//                Float mlong = Float.parseFloat(getIntent().getStringExtra("Long"));
//                LatLng ltlg = new LatLng(lat, mlong);
//                mMap.setMyLocationEnabled(true);
//                mMap.addMarker(new MarkerOptions().position(ltlg).title(getIntent().getStringExtra("MARK")).snippet("" + position));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlg, 15));
//                mMap.animateCamera(CameraUpdateFactory.zoomIn());
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
//
//
//            }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        int poisition = Integer.parseInt(marker.getSnippet());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ADDRESS, mDatalist.get(poisition).getAddress());
        bundle.putString(Constants.INFO, mDatalist.get(poisition).getGroundInfo());
        bundle.putString(Constants.PHONENO, mDatalist.get(poisition).getPhoneNum());
        bundle.putString(Constants.GROUNDNAME, mDatalist.get(poisition).getGroundName());
        bundle.putString(Constants.AREA, mDatalist.get(poisition).getArea());
        bundle.putString(Constants.CITY, mDatalist.get(poisition).getCity());

        if (mDatalist.get(poisition).getImageurls() != null)
            bundle.putStringArrayList(Constants.IMAGES, new ArrayList<String>(mDatalist.get(poisition).getImageurls()));

        startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("Lat", mDatalist.get(poisition).getLat()).putExtra("Long", mDatalist.get(poisition).getMlong()).putExtra("MARK", mDatalist.get(poisition).getGroundName()).putExtras(bundle));
        //            startActivity(new Intent(this, DetailActivity.class).putExtra("Lat", mDatalist.get(poisition).getLat()).putExtra("Long", mDatalist.get(poisition).getMlong()).putExtra("MARK", mDatalist.get(poisition).getGroundName()));

        return false;
    }
}
