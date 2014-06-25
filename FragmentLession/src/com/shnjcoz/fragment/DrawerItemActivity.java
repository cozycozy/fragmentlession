package com.shnjcoz.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DrawerItemActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private View mDrawerView;
	private ActionBarDrawerToggle mDrawerToggle;
    private String[] mNavigationTitles;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer_item);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerView = findViewById(R.id.drawer_frame);
		Log.i("DrawerItemActivity",mDrawerView.toString());

		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
				R.drawable.ic_drawer,
				R.string.drawer_open,
				R.string.drawer_close
				)
		{
			@Override
			public void onDrawerClosed(View drawerView){
				super.onDrawerClosed(drawerView);
				invalidateOptionsMenu();
			}
			
			@Override
			public void onDrawerOpened(View drawerView){
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
			
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);;
		
		setupNavDrawerList();
		
		if(savedInstanceState == null){
			selectItem(0);
		}
	}

	
	private void setupNavDrawerList() {
		mNavigationTitles = getResources().getStringArray(R.array.drawer_items);
		
		View.OnClickListener listener = new View.OnClickListener(){
				
			@Override
			public void onClick(View v) {
				int id = v.getId();
				switch (id) {
				case R.id.collections:
					selectItem(0);
					break;
				case R.id.send:
					selectItem(1);
					break;
				case R.id.favorites:
					selectItem(2);
					break;
				case R.id.share:
					selectItem(3);
					break;
				}
			}
			
		};
		Log.i("DrawerItemActivity",mDrawerView.toString());
		listener.toString();
		mDrawerView.findViewById(R.id.collections).setOnClickListener(listener);
		mDrawerView.findViewById(R.id.send).setOnClickListener(listener);
		mDrawerView.findViewById(R.id.favorites).setOnClickListener(listener);
		mDrawerView.findViewById(R.id.share).setOnClickListener(listener);
	
	}
	
	private void selectItem(int i) {
		
		Fragment fragment = DrawerItemFragment.getInstance(mNavigationTitles[i]);
		getFragmentManager().beginTransaction()
			.replace(R.id.content_frame, fragment).commit();
		
		mDrawerLayout.closeDrawer(mDrawerView);
		
	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // 4. onPostCreated()でActionBarDrawerToggleのsyncState()
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // 5. onConfigurationChanged()で
        // ActionBarDrawerToggleのonConfigurationChanged()を呼ぶ
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 6. onOptionsItemSelected()でActionBarDrawerToggleの
        // onOptionsItemSelected() を呼んで、ホームボタンが押されたときの処理を行う
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // ホームボタン以外の通常のAction Itemの処理を行う

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Navigation Drawerが開いていたら R.id.action_addのメニュー項目を表示しない
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerView);
        menu.findItem(R.id.action_add).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

}
