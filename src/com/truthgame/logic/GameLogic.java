package com.truthgame.logic;

import java.util.Random;

import com.truthgame.holders.MainActivityHolder;
import com.truthgame.holders.QuestionHolder;
import com.truthgame.R;

import android.app.Activity;
import android.app.Dialog;
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
	private int cardID;
	private QuestionMaker questionMaker;
	private WindowManager.LayoutParams lp;
	
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
		viewHolder.imgVolchock = (ImageView)rootView.findViewById(R.id.imageView_volchok);	
		viewHolder.layBottom = (FrameLayout)rootView.findViewById(R.id.bottom_frame);
		viewHolder.tvActionText = (TextView)rootView.findViewById(R.id.textView_action);
		viewHolder.imgVolchock.setOnClickListener(this);
		
		dialog();
		viewHolder.tvQuestion = (TextView) dialog.findViewById(R.id.textView_question_text);
		viewHolder.tvTitle = (TextView) dialog.findViewById(R.id.textView_title);
		viewHolder.okButton = (Button) dialog.findViewById(R.id.button_ok);
		viewHolder.cancelButton = (Button) dialog.findViewById(R.id.button_cancel);
		viewHolder.buttonsLayout = (LinearLayout) dialog.findViewById(R.id.linearLayout_buttons);
		viewHolder.cardImage = (ImageView) dialog.findViewById(R.id.imageView_card);
		viewHolder.okButton.setOnClickListener(this);
		viewHolder.cancelButton.setOnClickListener(this);
		
		lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.MATCH_PARENT;	    
	}
	
	private void reset() {
		viewHolder.tvActionText.setText(activity.getResources().getString(R.string.action_begin_text));
		viewHolder.layBottom.setBackgroundResource(R.drawable.img_bg_bottom);
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
			reset();
			break;
		case R.id.button_cancel:
			// ответить онлайн
			dialog.dismiss();
			reset();
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
		cardID = random.nextInt(30) / 10;		
		viewHolder.tvActionText.setText(actionsSequences[cardID]);
		switch (cardID) {
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
				dialogSetQuestionText(questionMaker.getQuestion(cardID));
				dialog.getWindow().setAttributes(lp);
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
	
	private void dialogSetQuestionText(QuestionHolder questionHolder) {
		viewHolder.tvTitle.setText(questionHolder.title);
		viewHolder.tvQuestion.setText(questionHolder.text);
		viewHolder.tvTitle.setBackgroundColor(questionHolder.color);
		viewHolder.tvQuestion.setBackgroundColor(questionHolder.color);
		viewHolder.buttonsLayout.setBackgroundColor(questionHolder.color);
		if(questionHolder.card != null) {
			viewHolder.cardImage.setBackground(questionHolder.card);
			viewHolder.cardImage.setVisibility(View.VISIBLE);
		}
		else {
			viewHolder.cardImage.setVisibility(View.GONE);
		}
	}
		
}
