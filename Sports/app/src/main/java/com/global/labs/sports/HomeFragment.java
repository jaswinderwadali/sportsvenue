package com.global.labs.sports;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


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
        WebService web = new WebService(searchbox.getText().toString(), getContext());
        web.Result(new ResultBack() {
            @Override
            public void Result(String str, boolean status) {
                dilog.dismiss();
                if (status)
                    getContext().startActivity(new Intent(getContext(), ResultActivity.class).putExtra("DATA", str));
                else
                    Snackbar.make(getActivity().findViewById(R.id.textView), "Error", Snackbar.LENGTH_SHORT).show();
            }
        });
        web.execute();
    }


}
