package com.global.labs.utils;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class Internet_Callback extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getExtras() != null) {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {

//				ComponentName comp = new ComponentName(
//						context.getPackageName(), SyncService.class.getName());

//				startWakefulService(context, (intent.setComponent(comp)));
				setResultCode(Activity.RESULT_OK);
				Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();

			} else if (intent.getBooleanExtra(
					ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
				Toast.makeText(context, "DisConnected", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}
}
