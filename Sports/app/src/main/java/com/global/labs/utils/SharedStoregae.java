package com.global.labs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedStoregae {

	SharedPreferences myshared_pregrence;

	private static SharedStoregae instance = null;

	private SharedStoregae(Context context) {
		myshared_pregrence = context.getSharedPreferences("CPD",
				Context.MODE_PRIVATE);
	}

	public static SharedStoregae getInstance(Context context) {
		if (instance == null) {
			synchronized (SharedStoregae.class) {
				if (instance == null) {
					instance = new SharedStoregae(context);
				}
			}
		}
		return instance;
	}

	public boolean FirstrunCheck() {
		if (myshared_pregrence.contains("F"))
			return true;
		else
			return false;
	}

	public void FirstRun() {
		Editor editor = myshared_pregrence.edit();
		editor.putBoolean("F", true);
		editor.commit();

	}

	public void verify() {
		Editor editor = myshared_pregrence.edit();
		editor.putBoolean("DONE", true);
		editor.commit();

	}

	public void logout() {
		Editor editor = myshared_pregrence.edit();
		editor.putBoolean("DONE", false);
		editor.commit();

	}

	public boolean verifychcek() {
		if (myshared_pregrence.contains("DONE"))
			return true;
		else
			return false;
	}

	public void SetRequest_id(String banner) {
		Editor editor = myshared_pregrence.edit();
		editor.putString("request_id", banner);
		editor.commit();
	}

	public void DeviceToken(String banner) {
		Editor editor = myshared_pregrence.edit();
		editor.putString("DEVICRTOKEN", banner);
		editor.commit();
	}

	public String GetDeviceToken() {
		if (myshared_pregrence.contains("DEVICRTOKEN")) {
			return myshared_pregrence.getString("DEVICRTOKEN", "0");
		} else {
			return "0";
		}
	}

	public String GetLastIndex(String GroupID) {
		if (myshared_pregrence.contains("G" + GroupID)) {
			return myshared_pregrence.getString("G" + GroupID, "0");
		} else {
			return "0";
		}
	}

	public void PutLastIndex(String GroupID, String ID) {
		Editor editor = myshared_pregrence.edit();
		editor.putString("G" + GroupID, ID);
		editor.commit();

	}

	public int GetPostSort(String GroupID) {
		if (myshared_pregrence.contains("PSHORT" + GroupID)) {
			return myshared_pregrence.getInt("PSHORT" + GroupID, 0);
		} else {
			return 0;
		}
	}

	public void SetPostSort(String GroupID, int Position) {
		Editor editor = myshared_pregrence.edit();
		editor.putInt("PSHORT" + GroupID, Position);
		editor.commit();

	}

	public int Dialogone() {
		if (myshared_pregrence.contains("Dialogone")) {
			return myshared_pregrence.getInt("Dialogone", 0);
		} else {
			return 0;
		}
	}

	public void PutDialogone(int ID) {
		Editor editor = myshared_pregrence.edit();
		editor.putInt("Dialogone", ID);
		editor.commit();

	}

	public String GetReq_id() {
		return myshared_pregrence.getString("request_id", "");
	}

	public void MobileNo(String mobile) {
		Editor editor = myshared_pregrence.edit();
		editor.putString("PHONENO", mobile);
		editor.commit();
	}

	public String GetMobile() {
		return myshared_pregrence.getString("PHONENO", "");
	}

	public void profilepic(String profilepic) {
		Editor editor = myshared_pregrence.edit();
		editor.putString("USERIMAGE", profilepic);
		editor.commit();
	}

	public String profilepic() {
		return myshared_pregrence.contains("USERIMAGE") ? myshared_pregrence
				.getString("USERIMAGE", "") : "";
	}

	public void USERNAME(String USERNAME) {
		Editor editor = myshared_pregrence.edit();
		editor.putString("USERNAME", USERNAME);
		editor.commit();
	}

	public String USERNAME() {

		return myshared_pregrence.contains("USERNAME") ? myshared_pregrence
				.getString("USERNAME", "") : "";
	}

	public void clear() {
			myshared_pregrence.edit().clear().commit();

	}

}
