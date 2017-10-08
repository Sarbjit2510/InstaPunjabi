package com.instapunjabiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreListviewAdapter extends BaseAdapter {

	Context context;
	public MoreListviewAdapter(Context c) {
		context=c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return MoreActivity.moretxt.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return MoreActivity.moretxt.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.moresingle_item, parent,false);
		ImageView img=(ImageView) row.findViewById(R.id.moreimageView1);
		TextView txt=(TextView) row.findViewById(R.id.moretextView);
		img.setImageResource(MoreActivity.moreimages.get(position));
		txt.setText(MoreActivity.moretxt.get(position));
		
		return row;
	}

}
