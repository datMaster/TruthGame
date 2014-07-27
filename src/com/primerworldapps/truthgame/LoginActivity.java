package com.primerworldapps.truthgame;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LoginActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, OnClickListener {

	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/*
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;
	private ProgressDialog progressDialog;
	private ParseUser user;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();

		screenInit();
	}

	private void screenInit() {
		findViewById(R.id.btn_sign_in).setOnClickListener(this);
		findViewById(R.id.button_leter).setOnClickListener(this);
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!mIntentInProgress && result.hasResolution()) {
			try {
				mIntentInProgress = true;
				result.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent. Return to the
				// default
				// state and attempt to connect to get an updated
				// ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
		if (currentPerson != null) {

			user = new ParseUser();
			user.setUsername(currentPerson.getDisplayName());

			user.setPassword("my-pass");

			user.setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
			user.put("avatar", currentPerson.getImage().getUrl());
			doAuth(user);
		} else {
			progressDialog.dismiss();
			Toast.makeText(LoginActivity.this, getString(R.string.gplus_null), Toast.LENGTH_SHORT).show();
		}

	}

	protected void doAuth(ParseUser user) {
		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				signIn();
			}
		});
	}

	protected void signIn() {
		Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
		ParseUser.logInInBackground(currentPerson.getDisplayName(), "my-pass", new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					progressDialog.dismiss();
					Toast.makeText(LoginActivity.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();
					finish();
				} else {
					progressDialog.dismiss();
					Toast.makeText(LoginActivity.this, getString(R.string.error_code) + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_leter:
			finish();
			break;

		case R.id.btn_sign_in:
			startGooglePlus();
			break;
		}
	}

	public void startGooglePlus() {
		if (!mGoogleApiClient.isConnected()) {
			mGoogleApiClient.connect();
			progressDialog = ProgressDialog.show(this, getString(R.string.connection), getString(R.string.connecting_auth));

		}
	}

}
