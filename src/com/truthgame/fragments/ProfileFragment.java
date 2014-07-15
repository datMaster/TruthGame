package com.truthgame.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.truthgame.R;
import com.truthgame.utils.DownloadImage;

public class ProfileFragment {
		
	private View rootView;
	
	private TextView tvUserName;
	private TextView tvUserMail;
	private TextView tvLoading;
	private ImageView avatar;
	
	public ProfileFragment(View rootView) {	
		this.rootView = rootView;		
		initialization();
	}		
	
	private void initialization() {
		
		tvUserName = (TextView) rootView.findViewById(R.id.textView_user_name);
		tvUserMail = (TextView) rootView.findViewById(R.id.textView_user_mail);
		tvLoading = (TextView) rootView.findViewById(R.id.textView_loading);
		avatar = (ImageView) rootView.findViewById(R.id.imageView_avatar);
		
		tvUserName.setText(ParseUser.getCurrentUser().getUsername());
		tvUserMail.setText(ParseUser.getCurrentUser().getEmail());		
		
		new DownloadImage(avatar, tvLoading)
        .execute(ParseUser.getCurrentUser().getString("avatar"));
	
	}	
}


