package com.shnjcoz.fragment;

import java.util.HashMap;

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

public class IndexActivity extends Activity implements OnItemClickListener{

	private ArrayAdapter<String> adapter;
	private HashMap<Integer, String> listValue = new HashMap<Integer, String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		ListView listView = (ListView) findViewById(R.id.listView1);
		makeListValue();
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		for(int i=0; i < listValue.size(); i++){
			String[] str = listValue.get(i).split("/");
			adapter.add(str[0]);		
		}
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
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
	
	private void makeListValue(){
		listValue.put(0, "FixedTab/FixedTabActivity");
		listValue.put(1, "Component/ComponentActivity");
		listValue.put(2, "Spinner/SpinnerActivity");
		listValue.put(3, "CustomeActionBar/CustomizedActionBar");
		listValue.put(4, "ActionItem1/ActionItem1Activity");
	}
	

}
