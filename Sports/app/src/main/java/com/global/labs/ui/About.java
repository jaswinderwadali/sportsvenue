package com.global.labs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.global.labs.R;

/**
 * Created by Mantra on 9/7/2015.
 */
public class About extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about, container, false);
        NavigationActivity.resultnotfound=true;
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        NavigationActivity.resultnotfound = false;
    }

}
