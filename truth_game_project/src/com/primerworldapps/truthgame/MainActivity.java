package com.primerworldapps.truthgame;

import io.presage.Presage;
import io.presage.utils.IADHandler;

import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;
import com.primerworldapps.truthgame.appRater.AppRater;
import com.primerworldapps.truthgame.fragments.MoreFragment;
import com.primerworldapps.truthgame.fragments.RulesFragment;
import com.primerworldapps.truthgame.logic.GameLogic;

public class MainActivity extends FragmentActivity implements TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	private static boolean isStarted = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Presage.getInstance().setContext(this.getBaseContext());
		Presage.getInstance().start();

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		if (ParseUser.getCurrentUser() == null && !isStarted) {
			loginActivity();
			isStarted = true;
		}

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
		AppRater.app_launched(this);
	}
	
	@Override
	protected void onResume() {
	  super.onResume();

	  Presage.getInstance().adToServe("interstitial", new IADHandler() {

	    @Override
	    public void onAdNotFound() {
	      Log.i("PRESAGE", "ad not found");
	    }

	    @Override
	    public void onAdFound() {
	      Log.i("PRESAGE", "ad found");
	    }

	    @Override
	    public void onAdClosed() {
	      Log.i("PRESAGE", "ad closed");
	    }
	  });
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.dialog_exit_title)).setMessage(getResources().getString(R.string.dialog_exit_text))
				.setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}

				}).setIcon(R.drawable.ic_launcher).show();
	}

	private void loginActivity() {
		startActivity(new Intent(this, LoginActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.profile:
			if (ParseUser.getCurrentUser() != null)
				startActivity(new Intent(this, ProfileActivity.class));
			else
				loginActivity();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = null;
			switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
			case 1: {
				rootView = inflater.inflate(R.layout.fragment_main_upd, container, false);
				new GameLogic(getActivity(), rootView);
				break;
			}
			case 2: {
				rootView = inflater.inflate(R.layout.fragment_rules, container, false);
				new RulesFragment(getActivity(), rootView);
				break;
			}
			case 3: {
				rootView = inflater.inflate(R.layout.fragment_more, container, false);
				new MoreFragment(getActivity(), rootView);
				break;
			}
			default:
				break;
			}
			return rootView;
		}
	}

}
