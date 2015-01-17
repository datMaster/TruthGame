package com.primerworldapps.truthgame;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;

import android.app.Application;

public class TruthApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ParseCrashReporting.enable(this);
		Parse.initialize(this, "g39Xh682yGrw6CLBvQTsyFJPXdCr7oFbvxB33aBl", "VG7Hpcep5cDPGoFdpASiTZVbsUgwPvkmrJII70i3");
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}

}
