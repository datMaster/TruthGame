package com.truthgame.fragments;

import com.primerworldapps.truthgame.R;
import com.truthgame.adapters.MoreListAdapter;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

public class MoreFragment {
	
	private Activity activity;
	private View rootView;
	private ListView listView;
	
	public MoreFragment(Activity activity, View rootView) {
		this.activity = activity;
		this.rootView = rootView;		
		this.listView = (ListView) this.rootView.findViewById(R.id.ListView_more);
		
		setListViewAdapter();		
	}
	
	private void setListViewAdapter() {
		MoreListAdapter adapter = new MoreListAdapter(activity);
		listView.setAdapter(adapter);
	}
}
