package com.primerworldapps.truthgame.logic;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primerworldapps.truthgame.Constants;
import com.primerworldapps.truthgame.R;
import com.primerworldapps.truthgame.holders.MainActivityHolder;
import com.primerworldapps.truthgame.holders.QuestionHolder;

public class GameLogic extends Activity implements OnClickListener, AnimationListener {
	
	private Activity activity;
	private MainActivityHolder viewHolder;
	private View rootView;
	private Animation animationVolchock;
	private Animation animationSlectCard;
	private Random random;
	private CharSequence[] actionsSequences;
	private Dialog dialog;
	private int cardID;
	private QuestionMaker questionMaker;
	
	public GameLogic(Activity activ, View rootView) {
		this.activity = activ;
		this.rootView = rootView;
		this.animationVolchock = AnimationUtils.loadAnimation(this.activity, R.anim.animatios);
		this.animationVolchock.setAnimationListener(this);		
		this.animationSlectCard = AnimationUtils.loadAnimation(this.activity, R.anim.selectcard);
		this.viewHolder = new MainActivityHolder();
		this.actionsSequences = this.activity.getResources().getTextArray(R.array.actions);
		this.random = new Random();
		this.questionMaker = new QuestionMaker(this.activity);
		setAnimationListener();
		init();
	}
	
	
	private void init() {
		viewHolder.imgVolchock = (ImageView)rootView.findViewById(R.id.imageView_volochok_upd);	
		viewHolder.bgFrame = (FrameLayout)rootView.findViewById(R.id.frameLayout_bg);
		viewHolder.tvActionText = (TextView)rootView.findViewById(R.id.textView_status_upd);
		viewHolder.imgVolchock.setOnClickListener(this);
		
		dialogInit();
		viewHolder.tvQuestion = (TextView) dialog.findViewById(R.id.textView_question_text_upd);
		viewHolder.lnLayoutBg = (LinearLayout) dialog.findViewById(R.id.LinearLayout_bg_upd);
		viewHolder.tvTitle = (TextView) dialog.findViewById(R.id.textView_title_upd);
		viewHolder.rotateButton = (Button) dialog.findViewById(R.id.button_rotate_upd);
		viewHolder.onlineButton = (Button) dialog.findViewById(R.id.button_online_upd);
		viewHolder.cardImage = (ImageView) dialog.findViewById(R.id.imageView_card_upd);		
		viewHolder.rotateButton.setOnClickListener(this);
		viewHolder.onlineButton.setOnClickListener(this);
		
	}
	
	private void reset() {
		viewHolder.tvActionText.setText(activity.getString(R.string.action_begin_text));
		viewHolder.bgFrame.setBackgroundResource(R.drawable.bground_main);
	}


	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.imageView_volochok_upd:			
			viewHolder.imgVolchock.startAnimation(animationVolchock);		
			break;
		case R.id.button_rotate_upd:
			dialog.dismiss();
			reset();
			break;
		case R.id.button_online_upd:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, 
					activity.getString(R.string.online_answer) 
					+ " " + viewHolder.tvQuestion.getText().toString() 
					+ "\n- ");
			sendIntent.setType("text/plain");
						
			activity.startActivity(Intent.createChooser(sendIntent, activity.getString(R.string.send_via)));						
			break;					
		}
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        dialog.dismiss();
	    	reset();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onAnimationStart(Animation animation) {
		viewHolder.tvActionText.setText(activity.getString(R.string.action_progress_text));
	}


	@Override
	public void onAnimationEnd(Animation animation) {
		cardID = random.nextInt(Constants.MAX_QUESTION_RANDOM) / Constants.QUESTION_RANDOM_DIV;		
		viewHolder.tvActionText.setText(actionsSequences[cardID]);
		switch (cardID) {
		case 0:								
			viewHolder.bgFrame.startAnimation(animationSlectCard);			
			viewHolder.bgFrame.setBackgroundResource(R.drawable.yellow_bg);
			break;
		case 1:			
			viewHolder.bgFrame.startAnimation(animationSlectCard);			
			viewHolder.bgFrame.setBackgroundResource(R.drawable.red_bg);
			break;
		case 2:						
			viewHolder.bgFrame.startAnimation(animationSlectCard);
			viewHolder.bgFrame.setBackgroundResource(R.drawable.blue_bg);			
			break;
		default :						
			viewHolder.bgFrame.startAnimation(animationSlectCard);
			viewHolder.bgFrame.setBackgroundResource(R.drawable.white_bg);
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
			}			
			@Override
			public void onAnimationRepeat(Animation animation) {				
			}			
			@Override
			public void onAnimationEnd(Animation animation) {
				dialogSetQuestionText(questionMaker.getQuestion(cardID));				
				dialog.show();						
			}
		});
	}
		
	public void dialogInit() {
	    dialog = new Dialog(activity);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    dialog.setContentView(R.layout.question_layout_upd);	
	    dialog.setCancelable(false);
	    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));		    
		dialog.getWindow().getAttributes().windowAnimations = R.style.QuestionDialogAnimation;
	}
	
	private void dialogSetQuestionText(QuestionHolder questionHolder) {		
		viewHolder.tvTitle.setText(questionHolder.title);									
//		viewHolder.rotateButton.setBackgroundResource(questionHolder.buttonColor);
		viewHolder.lnLayoutBg.setBackgroundResource(questionHolder.color);

		if(questionHolder.card != -1) {
			viewHolder.tvQuestion.setVisibility(View.GONE);
			viewHolder.cardImage.setVisibility(View.VISIBLE);
			viewHolder.cardImage.setBackgroundResource(questionHolder.card);			
		}
		else {
			viewHolder.cardImage.setVisibility(View.GONE);	
			viewHolder.tvQuestion.setVisibility(View.VISIBLE);
			viewHolder.tvQuestion.setText(questionHolder.text);
		}
	}	
}
