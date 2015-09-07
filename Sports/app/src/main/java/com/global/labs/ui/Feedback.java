package com.global.labs.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.global.labs.sports.R;

/**
 * Created by Mantra on 9/7/2015.
 */
public class Feedback extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:mikhail.mitra@mantralabsglobal.com?subject=" + getActivity().getResources().getString(R.string.app_name)
                        + "&body=" + "Help and Feedback");
                intent.setData(data);
                startActivity(intent);
            }
        });

        return view;
    }
}
