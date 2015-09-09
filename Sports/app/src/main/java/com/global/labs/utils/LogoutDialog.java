package com.global.labs.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.global.labs.R;

public class LogoutDialog extends Dialog {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.layout_snackbar);
		TextView tv = (TextView) findViewById(R.id.textView);
		tv.setText(message);
//		Button btn = (Button) findViewById(R.id.button1);
//		btn.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				dismiss();
//			}
//		});

	}

	String message = "";

	public LogoutDialog(Context context, String Message) {
		super(context);
		this.message = Message;
		show();

	}
}
