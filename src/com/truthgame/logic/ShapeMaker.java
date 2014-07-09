package com.truthgame.logic;

import com.truthgame.Constants;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class ShapeMaker {

	private GradientDrawable gradient;
	
	public ShapeMaker(Activity activity, int color) {
    
	    gradient = new GradientDrawable();
	    gradient.setColor(activity.getResources().getColor(color));
	    gradient.setCornerRadius(Constants.QUESTION_RADIUS_CORNER);
	    gradient.setStroke(Constants.QUESTION_STROKE, Color.WHITE);
	}
	
	public GradientDrawable getGradient(){
		return gradient;
	}
}
