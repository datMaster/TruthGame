package com.primerworldapps.truthgame.fragments;

import com.primerworldapps.truthgame.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Rulesfragment implements OnClickListener{

	private Activity activity;	
	
	public Rulesfragment(Activity activity, View v) {
		this.activity = activity;
		Button buttonRate = (Button) v.findViewById(R.id.button_rate);
		buttonRate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {		
		String link = activity.getString(R.string.rate_link);
		activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
	}
	
}
