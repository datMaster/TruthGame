package com.truthgame.logic;

import java.util.Random;

import com.truthgame.holders.MainActivityHolder;
import com.truthgame.R;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
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
	
	public GameLogic(Activity activity, View rootView) {
		this.activity = activity;
		this.rootView = rootView;
		this.animationVolchock = AnimationUtils.loadAnimation(this.activity, R.anim.animatios);
		this.animationVolchock.setAnimationListener(this);		
		this.animationSlectCard = AnimationUtils.loadAnimation(this.activity, R.anim.selectcard);
		this.viewHolder = new MainActivityHolder();
		this.actionsSequences = this.activity.getResources().getTextArray(R.array.actions);
		this.random = new Random();		
		init();
	}
	
	
	private void init() {
		viewHolder.imgVolchock = (ImageView)rootView.findViewById(R.id.imageView_volchok);	
		viewHolder.layBottom = (FrameLayout)rootView.findViewById(R.id.bottom_frame);
		viewHolder.tvActionText = (TextView)rootView.findViewById(R.id.textView_action);
		viewHolder.imgVolchock.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		viewHolder.imgVolchock.startAnimation(animationVolchock);						
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
			viewHolder.layBottom.setAnimation(animationSlectCard);
			viewHolder.layBottom.setBackgroundResource(R.drawable.card_yellow);
			break;
		case 1:			
			viewHolder.layBottom.setAnimation(animationSlectCard);
			viewHolder.layBottom.setBackgroundResource(R.drawable.card_red);
			break;
		case 2:			
			viewHolder.layBottom.setAnimation(animationSlectCard);
			viewHolder.layBottom.setBackgroundResource(R.drawable.card_blue);
			
			break;
		case 3:			
			viewHolder.layBottom.setAnimation(animationSlectCard);
			viewHolder.layBottom.setBackgroundResource(R.drawable.card_white);
			break;			
		default:
			break;
		}
	}


	@Override
	public void onAnimationRepeat(Animation animation) {
		
	}
}
