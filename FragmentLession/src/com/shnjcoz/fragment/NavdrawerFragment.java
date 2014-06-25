package com.shnjcoz.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NavdrawerFragment extends Fragment {

	public static NavdrawerFragment getInstance(String title){

		NavdrawerFragment navdrawerFragment = new NavdrawerFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		navdrawerFragment.setArguments(args);
		return navdrawerFragment;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_simple, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		super.onViewCreated(view, savedInstanceState);
		String title = getArguments().getString("title");
		TextView textView = (TextView) view.findViewById(R.id.title);
		textView.setText(title);
		
	}
	
	
	
}
