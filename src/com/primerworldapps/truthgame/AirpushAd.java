package com.primerworldapps.truthgame;

import com.dskuidp.xckswin94753.Prm;

import android.app.Activity;

public class AirpushAd {
	
	private static com.dskuidp.xckswin94753.Prm prm;		
	
	public static void init(Activity activity) {
		if(prm == null)
			prm = new Prm(activity, null, false);
	}
	
	public static void showAd() {
		prm.runOverlayAd();
	}
	
}
