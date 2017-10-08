package com.instapunjabiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class SmilyImageAdapter extends BaseAdapter {

	Context context;
	public SmilyImageAdapter(Context c) {
	context=c;
}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return FinalImage.smilyimages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return FinalImage.smilyimages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.gridimg3, parent, false);
		ImageView img=(ImageView) row.findViewById(R.id.imageView1);
		img.setImageResource(FinalImage.smilyimages.get(position));
		return row;
	}

}
