package com.global.labs.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


@SuppressWarnings("rawtypes")
public class UPdate_Image extends AsyncTask {

	String message;
	byte[] bytearray;

	private int ineternet_flag = 0;

	File file;
	String user_id;
	String uniqueid;
	String fileName;

	public UPdate_Image(File file, Context context, String user_id,
			String uniqueid, String filename) {
		this.fileName = filename;
		this.file = file;
		this.user_id = user_id;
		this.uniqueid = uniqueid;

	}

	// Interface_Result Result;

	public void getresult(Interface_Result Result) {
		// this.Result = Result;

	}

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

			URL url = new URL("http://rechat.net/rechat/iws/main.php");
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
			conn.setRequestProperty("file", fileName);
			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"user_id\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(user_id);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"unique_id\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(uniqueid);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"CatType\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes("UploadImage");
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename="
					+ fileName + "" + lineEnd);
			dos.writeBytes(lineEnd);

			if (file != null) {

				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap bitmap = BitmapFactory.decodeFile(
						file.getAbsolutePath(), option);
				// bitmap = Bitmap.createScaledBitmap(bitmap,200,200,true);

				ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG
						, 40, stream2);
				bytearray = stream2.toByteArray();

				ByteArrayInputStream in = new ByteArrayInputStream(bytearray);
				bytesAvailable = in.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];
				// read file and write it into form...
				bytesRead = in.read(buffer, 0, bufferSize);
				while (bytesRead > 0) {
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = in.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = in.read(buffer, 0, bufferSize);
				}
				in.close();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			ineternet_flag = 1;

		}

		return null;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (ineternet_flag != 1) {
//			JsonParsing parsing = new JsonParsing();
//			parsing.Maindata(message, true);

			// Result.Success(message);
		} else {
			// Result.Error(message);

		}

	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();

	}

}
