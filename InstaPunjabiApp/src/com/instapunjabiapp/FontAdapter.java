package com.instapunjabiapp;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;



public class FontAdapter extends BaseAdapter{

	Context mcontext;
	public FontAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mcontext=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return AddFont.images.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return AddFont.images.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater=(LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.single_row, parent, false);
		ImageView image=(ImageView) row.findViewById(R.id.imageView1);
		image.setImageResource(AddFont.images.get(position));			
		return row;
	}

}
