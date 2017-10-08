package com.instapunjabiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SocialsiteAdapter extends BaseAdapter {

	Context context;
	public SocialsiteAdapter(Context c) {
		this.context=c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ShareImage.socialitems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ShareImage.socialitems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.share_single_item, parent,false);
		ImageView shareimage=(ImageView) row.findViewById(R.id.shareimage);
		TextView sharetxt=(TextView) row.findViewById(R.id.sharetxt);
		shareimage.setImageResource(ShareImage.socialimages.get(position));
		sharetxt.setText(ShareImage.socialitems.get(position));
		return row;
	}

}
