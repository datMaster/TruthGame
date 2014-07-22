package com.truthgame.logic;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.truthgame.Constants;
import com.primerworldapps.truthgame.R;
import com.truthgame.holders.MainActivityHolder;
import com.truthgame.holders.QuestionHolder;

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
	private int questionCount;
	
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
		this.questionCount = 0;
		setAnimationListener();
		init();
	}
	
	
	private void init() {
		viewHolder.imgVolchock = (ImageView)rootView.findViewById(R.id.imageView_volochok_upd);	
		viewHolder.bgFrame = (FrameLayout)rootView.findViewById(R.id.frameLayout_bg);
		viewHolder.tvActionText = (TextView)rootView.findViewById(R.id.textView_status_upd);
		viewHolder.imgVolchock.setOnClickListener(this);
		
		dialogInit();
		viewHolder.tvQuestion = (TextView) dialog.findViewById(R.id.textView_question_text);
		viewHolder.tvTitle = (TextView) dialog.findViewById(R.id.textView_title);
		viewHolder.okButton = (Button) dialog.findViewById(R.id.button_ok);
		viewHolder.cancelButton = (Button) dialog.findViewById(R.id.button_cancel);
		viewHolder.buttonsLayout = (LinearLayout) dialog.findViewById(R.id.linearLayout_buttons);
		viewHolder.cardImage = (ImageView) dialog.findViewById(R.id.imageView_card);		
		viewHolder.relativeLayout = (RelativeLayout) dialog.findViewById(R.id.relativeLayout_question);
		viewHolder.okButton.setOnClickListener(this);
		viewHolder.cancelButton.setOnClickListener(this);
		
	}
	
	private void reset() {
		viewHolder.tvActionText.setText(activity.getString(R.string.action_begin_text));
		viewHolder.bgFrame.setBackgroundResource(R.drawable.bground_main);
	}


	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.imageView_volochok_upd:
			if(questionCount == Constants.QUESTION_COUNT_RECLAM){
				 AdBuddiz.showAd(activity);
				 questionCount = 0;
			}
			else {
				questionCount ++;
				viewHolder.imgVolchock.startAnimation(animationVolchock);
			}
						
			break;
		case R.id.button_ok:
			dialog.dismiss();
			reset();
			break;
		case R.id.button_cancel:
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
		case 3:						
			viewHolder.bgFrame.startAnimation(animationSlectCard);
			viewHolder.bgFrame.setBackgroundResource(R.drawable.white_bg);
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
	    dialog.setContentView(R.layout.question_layout);	
	    dialog.setCancelable(false);
	    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));		    
		dialog.getWindow().getAttributes().windowAnimations = R.style.QuestionDialogAnimation;
	}
	
	private void dialogSetQuestionText(QuestionHolder questionHolder) {
		viewHolder.tvTitle.setText(questionHolder.title);
		if(questionHolder.text == null) {
			viewHolder.tvQuestion.setVisibility(View.GONE);
		}
		else {
			viewHolder.tvQuestion.setVisibility(View.VISIBLE);
			viewHolder.tvQuestion.setText(questionHolder.text);			
		}
				
		viewHolder.relativeLayout.setBackground(questionHolder.color);

		if(questionHolder.card != null) {
			viewHolder.cardImage.setBackground(questionHolder.card);
			viewHolder.cardImage.setVisibility(View.VISIBLE);
		}
		else {
			viewHolder.cardImage.setVisibility(View.GONE);
		}
	}	
}
