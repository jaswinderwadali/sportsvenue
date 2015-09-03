package com.global.labs.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.global.labs.common.Constants;
import com.global.labs.common.JsonParsing;
import com.global.labs.common.SeachModel;
import com.global.labs.sports.R;
import com.global.labs.utils.ResultBack;
import com.global.labs.utils.WebService;

import org.json.JSONException;

import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {


    EditText searchbox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        view.findViewById(R.id.seacrhbutton).setOnClickListener(this);

        view.findViewById(R.id.advance_search).setOnClickListener(this);
        searchbox = (EditText) view.findViewById(R.id.search_box);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seacrhbutton:
                searchcall();
                break;
            case R.id.advance_search:
                startActivity(new Intent(getActivity(), AdvaceSearch.class));
                break;
            default:
                break;


        }
    }

    ProgressDialog dilog;

    private void searchcall() {
        dilog = new ProgressDialog(getContext());
        dilog.setMessage("Searching....");
        dilog.setCancelable(false);
        dilog.show();
        WebService web = new WebService("searchString=" + searchbox.getText().toString(), getContext(), Constants.URL + "/getSearchResults");
        web.Result(new ResultBack() {
            @Override
            public void Result(String str, boolean status) {
                dilog.dismiss();
                if (status)
                    if (JsonParsing.HasData(str))
                        getContext().startActivity(new Intent(getContext(), ResultActivity.class).putExtra("DATA", str));
                    else
                        Snackbar.make(getActivity().findViewById(R.id.textView), "Result Not Found..", Snackbar.LENGTH_SHORT).show();
                else
                    Snackbar.make(getActivity().findViewById(R.id.textView), "Error", Snackbar.LENGTH_SHORT).show();
            }
        });
        web.execute();
    }


}
