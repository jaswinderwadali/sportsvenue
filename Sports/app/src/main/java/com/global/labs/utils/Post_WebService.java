package com.global.labs.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


public class Post_WebService extends AsyncTask<Void, Void, Void> {

	String message;
	byte[] bytearray;
	String category;
	String group_id;
	String text;
	File file;
	String user_id;
	String uniqueid;
	String fileName, is_show;
	String Aprove, group_unique_id;
	boolean flag = true;

	public Post_WebService(File file, Context context, String user_id,
			String category, String group_id, String text, String uniqueid,
			String filename, String Aprove, String group_unique_id,
			String is_show) {
		this.fileName = filename;
		this.is_show = is_show;
		this.category = category;
		this.group_unique_id = group_unique_id;
		this.group_id = group_id;
		this.file = file;
		this.text = text;
		this.user_id = user_id;
		this.uniqueid = uniqueid;
		this.Aprove = Aprove;
	}

	@Override
	protected Void doInBackground(Void... params) {
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
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// conn.connect();
			conn.setRequestProperty("file", fileName);
			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"approved\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(Aprove);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"user_id\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(user_id);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"group_id\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(group_id);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"category\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(category);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"is_show\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(is_show);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"group_unique_id\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(group_unique_id);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"text\""
					+ lineEnd);
			dos.writeBytes(lineEnd);

			// assign value
			dos.writeBytes(text);
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
			dos.writeBytes("CreateChat");
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
				bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream2);
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

				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// FileInputStream fileInputStream = new FileInputStream(file);
				// bytesAvailable = fileInputStream.available();
				// bufferSize = Math.min(bytesAvailable, maxBufferSize);
				// buffer = new byte[bufferSize];
				// bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				// while (bytesRead > 0) {
				// dos.write(buffer, 0, bufferSize);
				// bytesAvailable = fileInputStream.available();
				// bufferSize = Math.min(bytesAvailable, maxBufferSize);
				// bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				// }
				// dos.writeBytes(lineEnd);
				// dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				// fileInputStream.close();
			}

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
			flag = false;
			e.printStackTrace();

		}

		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (flag) {
//			new JsonParsing().PostData(message);
		} else {

		}
	}

}
