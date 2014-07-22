package com.truthgame.logic;

import java.util.Random;

import com.primerworldapps.truthgame.R;
import com.truthgame.holders.QuestionHolder;
import com.truthgame.Constants;

import android.app.Activity;

public class QuestionMaker {
	
	private Activity activity;
	private CharSequence[] redQuestions;
	private CharSequence[] blueQuestions;
	private CharSequence[] yellowQuestions;	
	private Random random;
	private QuestionHolder holder;
	
	public QuestionMaker(Activity activity) {
		this.activity = activity;
		redQuestions = activity.getResources().getTextArray(R.array.red_questions);
		blueQuestions = activity.getResources().getTextArray(R.array.blue_questions);
		yellowQuestions = activity.getResources().getTextArray(R.array.yellow_questions);		
		random = new Random();
		holder = new QuestionHolder();
	}
	
	private void getCard() {
		int id = random.nextInt(Constants.MAX_CARDS);
		switch (id) {
		case Constants.JOKER_CARD:
			holder.card = activity.getResources().getDrawable(R.drawable.action_joker);
			holder.isJocker = true;
			break;
		case Constants.PLUS_ONE_CARD:
			holder.card = activity.getResources().getDrawable(R.drawable.action_more);
			break;
		case Constants.REDIRECT_CARD:
			holder.card = activity.getResources().getDrawable(R.drawable.action_change);
			break;
		default:
			holder.card = null;
			break;
		}
	}
	
	private void getQuestionAndTitle(CharSequence[] questions, int title, int color, int buttonColor) {
		getCard();
		if (questions != null) {
			if (!holder.isJocker) {
				int index = random.nextInt(questions.length - 1);
				holder.text = questions[index].toString();				
			}
			else {
				holder.isJocker = false;
				holder.text = null;
			}
		} else {
			holder.text = activity.getResources().getString(
					R.string.white_question);
		}
//		ShapeMaker shape = new ShapeMaker(activity, color);
		holder.title = activity.getResources().getString(title);
		holder.color = color; //shape.getGradient();
		holder.buttonColor = buttonColor;
	}
	
	public QuestionHolder getQuestion(int color) {
				
		switch(color) {
			case Constants.YELLOW_CARD : {
				getQuestionAndTitle(yellowQuestions, R.string.yellow_question_title, R.drawable.yellow_dialog_bg, R.drawable.yellow_button_rotate);// R.color.yellow_question_color);				
			}
				break;
			case Constants.RED_CARD : {
				getQuestionAndTitle(redQuestions, R.string.red_question_title, R.drawable.red_dialog_bg, R.drawable.red_button_rotate);// R.color.red_question_color);				
			}
				break;
			case Constants.BLUE_CARD : {
				getQuestionAndTitle(blueQuestions, R.string.blue_question_title, R.drawable.blue_dialog_bg, R.drawable.blue_button_rotate);// R.color.blue_question_color);				
			}
			break;			
			case Constants.WHITE_CARD : {
				getQuestionAndTitle(null, R.string.white_question_title, R.drawable.grey_dialog_bg, R.drawable.grey_button_rotate);//R.color.white_question_color);				
			}
			break;
		}
		return holder;
	}
}
