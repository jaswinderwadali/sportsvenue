package com.global.labs.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.global.labs.common.Constants;
import com.global.labs.common.JsonParsing;
import com.global.labs.sports.R;
import com.global.labs.utils.Internet;
import com.global.labs.utils.ResultBack;
import com.global.labs.utils.WebService;


public class HomeFragment extends Fragment implements View.OnClickListener {


    EditText searchbox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        view.findViewById(R.id.seacrhbutton).setOnClickListener(this);
        view.findViewById(R.id.advance_search).setOnClickListener(this);
        searchbox = (EditText) view.findViewById(R.id.search_box);
        spinersetup(view);
        return view;
    }

    String[] Sportarray = {"--Select City --", "Bangalore"};

    void spinersetup(View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.spiner_item, Sportarray);
        Spinner spiner = (Spinner) view.findViewById(R.id.spinerlocation);
        spiner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//      spiner.setOnItemSelectedListener(itemseletc);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seacrhbutton:
                if (Internet.getInstance().internetConnectivity(getActivity()))
                    searchcall();
                else
                    Toast.makeText(getActivity(), "Please Check Internet", Toast.LENGTH_SHORT).show();
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
        dilog = new ProgressDialog(getActivity());
        dilog.setMessage("Searching....");
        dilog.setCancelable(false);
        dilog.show();
        hidekeyboad();
        WebService web = new WebService("searchString=" + searchbox.getText().toString(), getActivity(), Constants.URL + "/getSearchResults");
        web.Result(new ResultBack() {
            @Override
            public void Result(String str, boolean status) {
                dilog.dismiss();
                if (status)
                    if (JsonParsing.HasData(str))
                        getActivity().startActivity(new Intent(getActivity(), ResultActivity.class).putExtra("DATA", str));
                    else {
                        getFragmentManager().beginTransaction().replace(R.id.container, new Result_Add()).commit();
                        Snackbar.make(getActivity().findViewById(R.id.textView), "Result Not Found..", Snackbar.LENGTH_SHORT).show();
                    }
                else
                    Snackbar.make(getActivity().findViewById(R.id.textView), "Error", Snackbar.LENGTH_SHORT).show();
            }
        });
        web.execute();
    }


    private void hidekeyboad() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchbox.getWindowToken(), 0);
    }


}
