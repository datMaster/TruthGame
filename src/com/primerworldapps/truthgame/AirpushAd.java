package com.primerworldapps.truthgame;

import android.app.Activity;

import com.xodtlisghp.hvlkujboys196547.Prm;

public class AirpushAd {
	
	private static Prm prm;		
	
	public static void init(Activity activity) {
		if(prm == null)
			prm = new Prm(activity, null, false);
	}
	
	public static void showAd() {
		prm.runOverlayAd();
	}
	
}
