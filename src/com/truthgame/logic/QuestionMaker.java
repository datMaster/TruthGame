package com.truthgame.logic;

import android.app.Activity;


public class QuestionMaker {
	
	private Activity activity;
	private CharSequence[] redWords;
	private CharSequence[] blueWords;
	private CharSequence[] yellowWords;
	private CharSequence[] whiteWords;
	
	public QuestionMaker(Activity activity) {
		this.activity = activity;		
	}
}
