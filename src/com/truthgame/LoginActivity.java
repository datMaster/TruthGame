package com.truthgame;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.truthgame.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class LoginActivity extends Activity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private ParseUser user;
	private SignInButton loginButton;
	private ProgressDialog progressDialog;

	public final int REQUEST_CODE_RESOLVE_ERR = 9000;
	private final int CANCEL = 0;

	private PlusClient plusClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		progressDialog = new ProgressDialog(this);
		plusClient = new PlusClient.Builder(this, this, this).setActions(
				"http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity").build();
		screenInit();
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			try {
				connectionResult.startResolutionForResult(this,
						REQUEST_CODE_RESOLVE_ERR);
			} catch (IntentSender.SendIntentException e) {
				plusClient.connect();
			}
		} else {
			Toast.makeText(
					this,
					getString(R.string.gplus_error_code)
							+ connectionResult.getErrorCode(),
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		if (plusClient != null) {
			user = new ParseUser();
			user.setUsername(plusClient.getCurrentPerson().getDisplayName());
			user.setPassword("my-pass");
			user.setEmail(plusClient.getAccountName());
			user.put("avatar", plusClient.getCurrentPerson().getImage()
					.getUrl());
			doAuth(user);
		} else {
			progressDialog.dismiss();
			Toast.makeText(LoginActivity.this, getString(R.string.gplus_null),
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onDisconnected() {
		progressDialog.dismiss();
		Toast.makeText(this, getString(R.string.disconnected),
				Toast.LENGTH_SHORT).show();

	}

	@Override
	protected void onStop() {
		super.onStop();
		plusClient.disconnect();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {
		case CANCEL: {
			progressDialog.dismiss();
		}
			break;
		case RESULT_OK: {
			plusClient.connect();
			progressDialog = ProgressDialog.show(this,
					getString(R.string.connection),
					getString(R.string.connecting_auth));
		}
			break;
		default:
			break;
		}
	}

	protected void signIn() {		
		ParseUser.logInInBackground(plusClient.getCurrentPerson()
				.getDisplayName(), "my-pass", new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					progressDialog.dismiss();
					Toast.makeText(LoginActivity.this,
							getString(R.string.welcome), Toast.LENGTH_SHORT)
							.show();
					finish();
				} else {
					progressDialog.dismiss();
					Toast.makeText(LoginActivity.this,
							getString(R.string.error_code) + e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}

	protected void doAuth(ParseUser user) {
		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				signIn();
			}
		});
	}

	private void screenInit() {
		loginButton = (SignInButton) findViewById(R.id.btn_sign_in);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGooglePlus();
			}
		});
	}

	protected void startGooglePlus() {
		if (!plusClient.isConnected()) {
			plusClient.connect();
			progressDialog = ProgressDialog.show(this,
					getString(R.string.connection),
					getString(R.string.connecting_auth));

		}
	}

}
