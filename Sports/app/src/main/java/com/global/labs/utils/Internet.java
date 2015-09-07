package com.global.labs.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Internet {
	private static  Internet INT;

	public static Internet getInstance() {
		if (INT == null)
			INT = new Internet();
		return INT;
	}
	public Boolean internetConnectivity(Context ctx) {
		ConnectivityManager conmgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiinfo = conmgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobiledata = conmgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return wifiinfo.isConnected() || mobiledata.isConnected();

	}
}

