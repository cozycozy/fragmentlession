package com.shnjcoz.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ActionItem1Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_item1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
