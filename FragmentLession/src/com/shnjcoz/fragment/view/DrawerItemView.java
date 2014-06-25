package com.shnjcoz.fragment.view;

import com.shnjcoz.fragment.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrawerItemView extends LinearLayout {

	public DrawerItemView(Context context) {
		this(context, null);
	}

	public DrawerItemView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public DrawerItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	
		TypedArray array = context.obtainStyledAttributes(
				attrs, com.shnjcoz.fragment.R.styleable.DrawerItemView,defStyle, 0);
	
		Drawable icon;
		String text;
		int count = - 1;
		
		try{
			icon = array.getDrawable(com.shnjcoz.fragment.R.styleable.DrawerItemView_src);
			text = array.getString(com.shnjcoz.fragment.R.styleable.DrawerItemView_text);
			count = array.getInt(com.shnjcoz.fragment.R.styleable.DrawerItemView_count, -1);
		}finally{
			array.recycle();
		}
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.drawer_list_item, this, true);
		
		((ImageView) findViewById(R.id.icon)).setImageDrawable(icon);
		((TextView)findViewById(android.R.id.text1)).setText(text);

		TextView tView = (TextView) findViewById(R.id.count);
		tView.setVisibility(count < 0 ? View.GONE : View.VISIBLE);
		tView.setText(String.valueOf(count));
	}

	
	
}
