package com.global.labs.sports;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        view.findViewById(R.id.advance_search).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        getContext().startActivity(new Intent(getContext(), AdvaceSearch.class));
    }
}
