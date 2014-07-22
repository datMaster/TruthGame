package com.truthgame.fragments;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.truthgame.LoginActivity;
import com.primerworldapps.truthgame.R;
import com.truthgame.utils.DownloadImage;

public class ProfileFragment implements OnClickListener{
		
	private View rootView;
	
	private TextView tvUserName;
	private TextView tvUserMail;
	private TextView tvLoading;
	private ImageView avatar;
	private Button logout;
	private Activity activity;
	
	
	public ProfileFragment(Activity activity, View rootView) {	
		this.rootView = rootView;
		this.activity = activity;
		initialization();
	}		
	
	private void initialization() {
		
		tvUserName = (TextView) rootView.findViewById(R.id.textView_user_name);
		tvUserMail = (TextView) rootView.findViewById(R.id.textView_user_mail);
		tvLoading = (TextView) rootView.findViewById(R.id.textView_loading);
		avatar = (ImageView) rootView.findViewById(R.id.imageView_avatar);
		logout = (Button) rootView.findViewById(R.id.button_logout);		
		logout.setOnClickListener(this);
		
		tvUserName.setText(ParseUser.getCurrentUser().getUsername());
		tvUserMail.setText(ParseUser.getCurrentUser().getEmail());		
		
		new DownloadImage(avatar, tvLoading)
        .execute(ParseUser.getCurrentUser().getString("avatar"));		
	
	}

	@Override
	public void onClick(View v) {
		ParseUser.logOut();
		activity.finish();
		activity.startActivity(new Intent(activity, LoginActivity.class));
	}	
}


