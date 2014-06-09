package com.shnjcoz.fragment;

import com.shnjcoz.fragment.common.HttpTask;
import com.shnjcoz.fragment.common.Callback;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter; 

public class IndexActivity extends Activity implements OnItemClickListener,Callback{

	private ArrayAdapter<String> adapter;
	private HashMap<Integer, String> listValue = new HashMap<Integer, String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		
		final HttpTask httpTask = new HttpTask(IndexActivity.this,"1");
		httpTask.execute();
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		String[] classnameString = listValue.get(pos).split("/",0);
		Log.i("IndexActivity", classnameString[0]);
		Log.i("IndexActivity", classnameString[1]);
		Log.i("IndexActivity", getPackageName());
		Intent intent = new Intent();
		intent.setClassName(getPackageName(), getPackageName() + "." + classnameString[1]);
		if(intent != null){
			intent.putExtra("color", adapter.getItem(pos));
			startActivity(intent);
		}
		
	}

	@Override
	public void callback(String responseCode, String requestCode,
			Map<String, JSONObject> resultMap) {
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);		
		try {
			JSONObject json = resultMap.get("responseJson");
			Log.i("IndexActivity","json :" + json.toString());
			JSONArray activityArray = json.getJSONArray("activity");
			for(int i = 0; i < activityArray.length(); i++){
				JSONObject activityObject = activityArray.getJSONObject(i);
				Log.i("indextActivity", activityObject.getString("key"));
				Log.i("indextActivity", activityObject.getString("DisplayName"));
				Log.i("indextActivity", activityObject.getString("ClassName"));
				listValue.put(i, activityObject.getString("DisplayName")+"/"+activityObject.getString("ClassName"));
			}			
		} catch (JSONException e) {
			// TODO: handle exception
		}

		for(int i=0; i < listValue.size(); i++){
			String[] str = listValue.get(i).split("/");
			adapter.add(str[0]);		
		}
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);		

	}
	

}
