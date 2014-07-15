package com.truthgame.adapters;

import java.util.ArrayList;

import com.truthgame.R;
import com.truthgame.holders.MoreViewHolder;

import android.app.Activity;
import android.net.Uri;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MoreListAdapter extends BaseAdapter implements OnClickListener {

	private Activity activity;
	private ArrayList<MoreViewHolder> holdersList;
	private LayoutInflater inflater;
	private CharSequence[] titles;
	private CharSequence[] descriptions;
	private CharSequence[] links;
	private TypedArray images;
	
	public MoreListAdapter(Activity activity) {
		this.activity = activity;
		this.holdersList = new ArrayList<MoreViewHolder>();	
		inflater = 
		(LayoutInflater) this.activity.getSystemService(this.activity.LAYOUT_INFLATER_SERVICE);
		initialization();
	}
	
	private void initialization() {
		titles = activity.getResources().getStringArray(R.array.more_titles);
		descriptions = activity.getResources().getStringArray(R.array.more_descr);
		images = activity.getResources().obtainTypedArray(R.array.more_img);
		links = activity.getResources().getStringArray(R.array.more_links);
		int titlesSize = titles.length;
		for(int i = 0; i < titlesSize; i ++) {
			MoreViewHolder newHolder = new MoreViewHolder();
			holdersList.add(newHolder);
		}
	}
	
	@Override
	public int getCount() {
		return holdersList.size();
	}

	@Override
	public Object getItem(int position) {
		return holdersList.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
			convertView = inflater.inflate(R.layout.more_list_view_tem, null);
		MoreViewHolder holder = holdersList.get(position);
		holder.title = (TextView) convertView.findViewById(R.id.textView_more_title);
		holder.descr = (TextView) convertView.findViewById(R.id.textView_more_description);
		holder.image = (ImageView) convertView.findViewById(R.id.imageView_more_image);
		holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout_more_list_item);
		holder.relativeLayout.setOnClickListener(this);
		holder.relativeLayout.setTag(position);
		holder.title.setText(titles[position].toString());
		holder.descr.setText(descriptions[position].toString());
		holder.image.setImageResource(images.getResourceId(position, -1));
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		int id = Integer.parseInt(v.getTag().toString());		
		activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(links[id].toString())));
	}
	

}
