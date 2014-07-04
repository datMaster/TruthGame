package com.truthgame.logic;

import java.util.Random;

import com.truthgame.holders.MainActivityHolder;
import com.truthgame.R;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GameLogic implements OnClickListener, AnimationListener {
	
	private Activity activity;
	private MainActivityHolder viewHolder;
	private View rootView;
	private Animation animationVolchock;
	private Animation animationSlectCard;
	private Random random;
	private CharSequence[] actionsSequences;
	private Dialog dialog;
	
	public GameLogic(Activity activ, View rootView) {
		this.activity = activ;
		this.rootView = rootView;
		this.animationVolchock = AnimationUtils.loadAnimation(this.activity, R.anim.animatios);
		this.animationVolchock.setAnimationListener(this);		
		this.animationSlectCard = AnimationUtils.loadAnimation(this.activity, R.anim.selectcard);
		this.viewHolder = new MainActivityHolder();
		this.actionsSequences = this.activity.getResources().getTextArray(R.array.actions);
		this.random = new Random();
		setAnimationListener();
		init();
	}
	
	
	private void init() {
		viewHolder.imgVolchock = (ImageView)rootView.findViewById(R.id.imageView_volchok);	
		viewHolder.layBottom = (FrameLayout)rootView.findViewById(R.id.bottom_frame);
		viewHolder.tvActionText = (TextView)rootView.findViewById(R.id.textView_action);
		viewHolder.imgVolchock.setOnClickListener(this);
		
		viewHolder.tvQuestion = (TextView) dialog.findViewById(R.id.textView_question_text);
		viewHolder.tvTitle = (TextView) dialog.findViewById(R.id.textView_title);
		viewHolder.okButton = (Button) dialog.findViewById(R.id.button_ok);
		viewHolder.cancelButton = (Button) dialog.findViewById(R.id.button_cancel);
		viewHolder.okButton.setOnClickListener(this);
		viewHolder.cancelButton.setOnClickListener(this);
		
		dialog();
	}


	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.imageView_volchok:
			viewHolder.imgVolchock.startAnimation(animationVolchock);
			break;
		case R.id.button_ok:
			// крутить волчок
			dialog.dismiss();
			break;
		case R.id.button_cancel:
			// ответить онлайн
			dialog.dismiss();
			break;

		default:
			break;
		}
	}


	@Override
	public void onAnimationStart(Animation animation) {
		viewHolder.tvActionText.setText(activity.getResources().getString(R.string.action_progress_text));
	}


	@Override
	public void onAnimationEnd(Animation animation) {
		int id = random.nextInt(30) / 10;		
		viewHolder.tvActionText.setText(actionsSequences[id]);
		switch (id) {
		case 0:								
			viewHolder.layBottom.startAnimation(animationSlectCard);			
			viewHolder.layBottom.setBackgroundResource(R.drawable.card_yellow);
			break;
		case 1:			
			viewHolder.layBottom.startAnimation(animationSlectCard);
			viewHolder.layBottom.setBackgroundResource(R.drawable.card_red);
			break;
		case 2:						
			viewHolder.layBottom.startAnimation(animationSlectCard);
			viewHolder.layBottom.setBackgroundResource(R.drawable.card_blue);			
			break;
		case 3:						
			viewHolder.layBottom.startAnimation(animationSlectCard);
			viewHolder.layBottom.setBackgroundResource(R.drawable.card_white);
			break;			
		default:
			break;
		}
	}


	@Override
	public void onAnimationRepeat(Animation animation) {
		
	}
	
	private void setAnimationListener() {
			animationSlectCard.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
						dialog.show();
				      
			}
		});
	}
	
	@SuppressWarnings("static-access")
	public void dialog() {
	    dialog = new Dialog(activity);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    dialog.setContentView(R.layout.question_layout);
	    dialog.setCancelable(false);	    	    
	}
	
	private void dialogSetQuestionText(String text) {
		viewHolder.tvQuestion.setText(text);
	}
		
}
