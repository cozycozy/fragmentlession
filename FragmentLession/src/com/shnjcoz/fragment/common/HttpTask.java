package com.shnjcoz.fragment.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class HttpTask extends AsyncTask<Void, Void, Void> {

	private Callback callback;
	
	private String requestCode;
	
	private String responseCode;
	
	private String fbid; 
	
	private Map<String, JSONObject> resultMap;
	
	public HttpTask(final Callback callback, final String requestCode){
		
		this.callback = callback;		
		this.requestCode = requestCode;
		this.responseCode = "";
		resultMap = new HashMap<String, JSONObject>();
	}
	
	public HttpTask(final Callback callback, String requestCode, String fbid) {
		this.callback = callback;		
		this.requestCode = requestCode;
		this.responseCode = "";
		this.fbid = fbid;
		resultMap = new HashMap<String, JSONObject>();
	}

	@Override
	protected void onPostExecute(Void result) {
		callback.callback(responseCode, requestCode, resultMap);
	}

	@Override
	protected Void doInBackground(Void... params) {
		Log.i("HttpTask","Start doInBackground");
		String url = "";
		if(requestCode == "1"){
			url = "http://10.0.2.2:9000/list";			
		}else if (requestCode == "2") {
			url = "http://10.0.2.2:9000/get?id=" + fbid;			
		}{
			url = "http://10.0.2.2:9000/list";
		}
		HttpGet request = new HttpGet( url );
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			JSONObject json = new JSONObject(EntityUtils.toString(entity));

			responseCode = json.getJSONArray("Status").getJSONObject(0).getString("status");
			
			if(responseCode.equals("200")){
				resultMap.put("responseJson", json);
			}else{
				Log.i("httpTask","status2: " + responseCode );				
			}
			Log.i("HttpTask","End doInBackground");
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}

}

