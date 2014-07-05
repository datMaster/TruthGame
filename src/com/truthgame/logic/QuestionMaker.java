package com.truthgame.logic;

import java.util.Random;

import com.truthgame.R;
import com.truthgame.holders.QuestionHolder;
import com.truthgame.Constants;

import android.app.Activity;

public class QuestionMaker {
	
	private Activity activity;
	private CharSequence[] redQuestions;
	private CharSequence[] blueQuestions;
	private CharSequence[] yellowQuestions;	
	private Random random;
	
	public QuestionMaker(Activity activity) {
		this.activity = activity;
		redQuestions = activity.getResources().getTextArray(R.array.red_questions);
		blueQuestions = activity.getResources().getTextArray(R.array.blue_questions);
		yellowQuestions = activity.getResources().getTextArray(R.array.yellow_questions);
		random = new Random();
	}
	
	public QuestionHolder getQuestion(int color) {
		QuestionHolder holder = new QuestionHolder();		
		switch(color) {
			case Constants.YELLOW_CARD : {
				int index = random.nextInt(yellowQuestions.length - 1);
				holder.text = yellowQuestions[index].toString();
				holder.title = activity.getResources().getString(R.string.yellow_question_title);
				holder.color = activity.getResources().getColor(R.color.yellow_question_color);
			}
				break;
			case Constants.RED_CARD : { 
				int index = random.nextInt(redQuestions.length - 1);
				holder.text = redQuestions[index].toString();
				holder.title = activity.getResources().getString(R.string.red_question_title);
				holder.color = activity.getResources().getColor(R.color.red_question_color);
			}
				break;
			case Constants.BLUE_CARD : {
				int index = random.nextInt(blueQuestions.length - 1);
				holder.text = blueQuestions[index].toString();
				holder.title = activity.getResources().getString(R.string.blue_question_title);
				holder.color = activity.getResources().getColor(R.color.blue_question_color);
			}
			break;			
			case Constants.WHITE_CARD : {
				holder.text = activity.getResources().getString(R.string.white_question);
				holder.title = activity.getResources().getString(R.string.white_question_title);
				holder.color = activity.getResources().getColor(R.color.white_question_color);
			}
			break;
		}
		return holder;
	}
}
