package com.global.labs.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.global.labs.R;
import com.global.labs.common.Constants;
import com.global.labs.common.DatabaseHelper;
import com.global.labs.common.JsonParsing;
import com.global.labs.utils.GetLatlong;
import com.global.labs.utils.Internet;
import com.global.labs.utils.ResultBack;
import com.global.labs.utils.WebService;

import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {


    private EditText searchbox;
    private GetLatlong gps;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123)
            gps = new GetLatlong(getActivity());
        if (!gps.getIsGPSTrackingEnabled()) {
            Toast.makeText(getActivity(), "GPS not Enable Properly", Toast.LENGTH_SHORT).show();
        } else {
            searchcall(true);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        view.findViewById(R.id.seacrhbutton).setOnClickListener(this);
        view.findViewById(R.id.advance_search).setOnClickListener(this);
        view.findViewById(R.id.searchnearby).setOnClickListener(this);
        searchbox = (EditText) view.findViewById(R.id.search_box);
        spinersetup(view);
        return view;
    }

    List<String> Sportarray;

    void spinersetup(View view) {
        try {
            Sportarray = new JsonParsing().jsonarraytolist(DatabaseHelper.getInstance(getActivity()).getMaintabledata("1").getCityList());
//            Sportarray.add(0,"--Select City --");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spiner_item, Sportarray);
            Spinner spiner = (Spinner) view.findViewById(R.id.spinerlocation);
            spiner.setAdapter(adapter);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } catch (Exception e) {

        }

//      spiner.setOnItemSelectedListener(itemseletc);
    }


    @Override
    public void onResume() {
        // manager();
        super.onResume();
    }

    void manager() {
        WebService web = new WebService("", getActivity().getApplicationContext(), Constants.URL + "/user/55f299028abc50d0703569ce");
        web.Result(new ResultBack() {
            @Override
            public void Result(String str, boolean falg) {
                Log.v("", "" + str);
                Toast.makeText(getActivity(), "" + str, Toast.LENGTH_SHORT).show();
            }
        });
        web.execute();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seacrhbutton:
                if (Internet.getInstance().internetConnectivity(getActivity()))
                    if (searchbox.getText().toString().trim().length() > 0)
                        searchcall(false);
                    else
                        Toast.makeText(getActivity(), "Please Enter Something", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Please Check Internet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.advance_search:
                startActivity(new Intent(getActivity(), AdvaceSearch.class));
                break;
            case R.id.searchnearby:
                mSearchnearabout();
            default:
                break;


        }
    }


    ProgressDialog dilog;

    private void searchcall(final boolean nearby) {

        if (nearby)
            searchbox.setText("");

        dilog = new ProgressDialog(getActivity());
        dilog.setMessage("Searching....");
        dilog.setCancelable(false);
        dilog.show();
        hidekeyboad();
        WebService web = new WebService("searchString=" + searchbox.getText().toString().trim() + "&searchCity=bangalore", getActivity(), Constants.URL + "/api/getSearchResults");
        web.Result(new ResultBack() {
            @Override
            public void Result(String str, boolean status) {
                dilog.dismiss();
                if (status)
                    if (JsonParsing.HasData(str))
                        if (!nearby)
                            getActivity().startActivity(new Intent(getActivity(), ResultActivity.class).putExtra("DATA", str));
                        else {

                            getActivity().startActivity(new Intent(getActivity(), ResultActivity.class).putExtra("DATA", str).putExtra("lat", gps.latitude).putExtra("mlong", gps.longitude));
                        }
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


    private void mSearchnearabout() {
        gps = new GetLatlong(getActivity());
        if (gps.getIsGPSTrackingEnabled()) {
            double lat = gps.longitude;
            double mlong = gps.longitude;
            searchcall(true);
        } else
            showSettingsAlert();
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Setting Dialog Title
        alertDialog.setTitle(R.string.app_name);
        // Setting Dialog Message
        alertDialog.setMessage("Please Enable Your GPS From Device Settings");
        // On Pressing Setting button
        alertDialog.setPositiveButton(R.string.action_settings,
                new DialogInterface.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 123);
                    }
                });

        // On pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    private void hidekeyboad() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchbox.getWindowToken(), 0);
    }


}
