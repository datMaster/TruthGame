package com.primerworldapps.truthgame;

import com.primerworldapps.truthgame.R;
import com.primerworldapps.truthgame.fragments.ProfileFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment(this)).commit();
		}
		
		ActionBar abar = getActionBar();
		abar.setDisplayHomeAsUpEnabled(true);
		abar.setDisplayShowHomeEnabled(false);
		abar.setDisplayShowTitleEnabled(true);	
		abar.setDisplayUseLogoEnabled(false);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return true;
	}

	public static class PlaceholderFragment extends Fragment {

		public Activity activity;
		
		public PlaceholderFragment(Activity activity) {
			this.activity = activity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_profile,
					container, false);
			new ProfileFragment(activity, rootView);
			return rootView;
		}
	}

}
