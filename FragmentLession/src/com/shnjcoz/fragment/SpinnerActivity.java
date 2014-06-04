package com.shnjcoz.fragment;

import com.shnjcoz.fragment.FixedTabActivity.SimpleFragment;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class SpinnerActivity extends Activity implements ActionBar.OnNavigationListener	{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar actionBar = getActionBar();
		
		actionBar.setDisplayShowTitleEnabled(false);
		
		actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_LIST);
		
		String[] data = new String[] {
			getString(R.string.title_section1),
			getString(R.string.title_section2),
			getString(R.string.title_section3),
			getString(R.string.title_section4)
		};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actionBar.getThemedContext(), 
				android.R.layout.simple_list_item_1,
				android.R.id.text1,
				data);
		
		actionBar.setListNavigationCallbacks(adapter, this);
		
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// コンテンツのFragmentを切替
		Fragment fragment = SimpleFragment.getInstance(itemPosition);
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, fragment)
			.commit();
		return true;
	}

	private static final String STATE_SELECTED_NAVI_ITEM = 
			"selected_navigation_item";

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if(savedInstanceState.containsKey(STATE_SELECTED_NAVI_ITEM)){
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVI_ITEM)
			);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(
				STATE_SELECTED_NAVI_ITEM, getActionBar().getSelectedNavigationIndex());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
