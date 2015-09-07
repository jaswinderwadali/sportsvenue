package com.global.labs.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import com.mar.adapter.LocalConstants;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.global.labs.common.Constants;

//import com.zib.httpmethods.UploadingImageresponce;
@SuppressWarnings("rawtypes")
public class Uploading extends AsyncTask {
	private ProgressDialog progress;
	String message;
	localinter myinterface;
	File file;
	String user_id;
	private Context context;

	boolean myflag = true;

	public void result(localinter myinterface) {
		this.myinterface = myinterface;
	}

	String filename = "";

	public Uploading(File file, Context context, String filename,
			String gropname) {
		this.file = file;
		this.context = context;
		this.filename = filename;
	}

	// public void getImageuploadresponce(UploadingImageresponce responce) {
	// this.responce = responce;
	// }
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		try {

			URL url = new URL(Constants.URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			// conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// conn.connect();
			conn.setRequestProperty("file", filename);
			dos = new DataOutputStream(conn.getOutputStream());

			
			
			
			
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"CatType\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes("IMAGESYNC");
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			
			
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename="
					+ filename + "" + lineEnd);
			dos.writeBytes(lineEnd);

			@SuppressWarnings("resource")
			FileInputStream fileInputStream = new FileInputStream(file);

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];
			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			// int serverResponseCode = conn.getResponseCode();
			// System.out.println("Code"+ serverResponseCode);
			// String serverResponseMessage = conn.getResponseMessage();

			InputStream mstream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					mstream, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			message = sb.toString();
			// close the streams //
			dos.flush();
			dos.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progress = new ProgressDialog(this.context);
		progress.setMessage("Please Wait...");
		progress.setCancelable(false);
		progress.show();

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progress.dismiss();
		// Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		System.out.println("XX" + message);
		myinterface.reslut(myflag);

	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		// responce.Error("Connectivity Error");
	}

	public interface localinter {
		void reslut(boolean flag);
	}

}
