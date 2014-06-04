package com.shnjcoz.fragment;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FixedTabActivity extends Activity implements ActionBar.TabListener {

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixed_tab);

        // タブを使うときはNAVIGATION_MODE_TABSをセットする
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ViewPagerAdapter adapter = new FixedTabActivity.ViewPagerAdapter(getFragmentManager());

        // ViewPagerにアダプターをセット
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        // ViewPagerのページ数だけタブを追加
        for (int i = 0; i < adapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(adapter.getPageTitle(i))
                    .setTabListener(this));
        }

        // ViewPagerのページが切り替わったら、選択されているタブの位置を変更
        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SimpleFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
            case 0:
                return getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab,
            FragmentTransaction fragmentTransaction) {
        // タブを選択されたら、ViewPagerのページをその位置に移動
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab,
            FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab,
            FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static class SimpleFragment extends Fragment {

        public static SimpleFragment getInstance(int position) {
            SimpleFragment f = new SimpleFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            f.setArguments(args);
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_simple, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            int position = getArguments().getInt("position");
            TextView tv = (TextView) view.findViewById(R.id.title);
            tv.setText("Page " + (position + 1));
        }
    }
}
