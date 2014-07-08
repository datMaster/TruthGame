package com.truthgame.logic;

import com.truthgame.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

public class ShapeMaker {
	private ShapeDrawable background = new ShapeDrawable();
	
	public ShapeMaker(Activity activity, int color) {
		int count = 8;
		float[] radii = new float[count];
		for(int i = 0; i < count; i ++)
			radii[i] = activity.getResources().getDimension(R.dimen.question_dimens);		
		
		background.setShape(new RoundRectShape(radii, null, null));		
		background.getPaint().setColor(activity.getResources().getColor(color));		

	}
	
	public ShapeDrawable getShapeDrawable() {
		return background;
	}
}
