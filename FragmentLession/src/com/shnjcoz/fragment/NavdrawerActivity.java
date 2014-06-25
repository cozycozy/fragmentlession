package com.shnjcoz.fragment;

import android.R.anim;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavdrawerActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private View mDrawerView;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] mNavigationTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);
	
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerView = findViewById(R.id.drawer_frame);
		
		mDrawerToggle = new ActionBarDrawerToggle(
				 this,
				 mDrawerLayout, 
				 R.drawable.ic_drawer,
				 R.string.drawer_open, 
				 R.string.drawer_close)
		{

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
			
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		
		setNavDrawerList();
		
		selectItem(0);
		
	}

	private void setNavDrawerList() {
		
		mNavigationTitle = getResources().getStringArray(R.array.drawer_items);
		ListView listView = (ListView) mDrawerView;
		
		listView.setAdapter(new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,mNavigationTitle));
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectItem(position);
			}

			
		});
		
	}

	private void selectItem(int position) {

		Fragment fragment = NavdrawerFragment.getInstance(mNavigationTitle[position]);
		getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment);
		mDrawerLayout.closeDrawer(mDrawerView);
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
		menu.findItem(R.id.action_add).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	
	
}
