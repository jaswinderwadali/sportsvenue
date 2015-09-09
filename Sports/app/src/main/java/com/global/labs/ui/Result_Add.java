package com.global.labs.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.global.labs.R;

public class Result_Add extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_result__add, container, false);
        view.findViewById(R.id.dousknow).setOnClickListener(this);
        view.findViewById(R.id.goback).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.dousknow:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:mikhail.mitra@mantralabsglobal.com?subject=" + getActivity().getResources().getString(R.string.app_name)
                        + "&body=" + "Do let us know");
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.goback:
                getFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                break;
        }

    }
}
