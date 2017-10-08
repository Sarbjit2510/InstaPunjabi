package com.instapunjabiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class InstaEmoAdapter extends BaseAdapter {

	Context context;
	public InstaEmoAdapter(Context c) {
		context=c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return FinalImage.instaimages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return FinalImage.instaimages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.gridimg1, parent, false);
		ImageView img=(ImageView) row.findViewById(R.id.imageView1);
		img.setImageResource(FinalImage.instaimages.get(position));
		return row;
	}

}
