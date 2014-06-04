package com.shnjcoz.fragment;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Listenerの登録
		setupClickListener(R.id.dynamicUiBook);
		setupClickListener(R.id.android4NewBook);
		setupClickListener(R.id.androidDbProgBook);
		setupClickListener(R.id.androidEngineBook);
		setupClickListener(R.id.androidSysDevBook);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		int resultId = 0;
		
		switch (id) {
		case R.id.dynamicUiBook:
			resultId = R.string.dynamicUiDescription;
			break;
		case R.id.android4NewBook:
			resultId = R.string.android4NewDescription;
			break;
		case R.id.androidDbProgBook:
			resultId = R.string.androidDbProgDescription;
			break;
		case R.id.androidEngineBook:
			resultId = R.string.androidEngineDescription;
			break;
		case R.id.androidSysDevBook:
			resultId = R.string.androidSysDevDescription;
			break;
		default:
			break;
		}
		
		if(resultId != 0){
			TextView bookDescTextView = (TextView) findViewById(R.id.bookDescription);
			bookDescTextView.setText(resultId);
		}
	}

	private void setupClickListener(int viewId){
		View childView = findViewById(viewId);
		childView.setOnClickListener(this);
	}
}
