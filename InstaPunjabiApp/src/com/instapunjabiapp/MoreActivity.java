package com.instapunjabiapp;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



public class MoreActivity extends Activity {

	static ArrayList<String> moretxt=new ArrayList<String>();
	static ArrayList<Integer> moreimages=new ArrayList<Integer>();
	private ListView morelistView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_more);
		CheckIn.listtxtBitmap = null;
		AddFont.txtBitmap = null;
		morelistView=(ListView) findViewById(R.id.morelistView);
		moretxt.clear();// more listview text
		moretxt.add("InstaPunjabi");
		moretxt.add("Rate App");
		moretxt.add("Share App");
		moretxt.add("Contact us");
		moretxt.add("Feedback");
		moretxt.add("Help");
		
		moreimages.clear();// more listview images
		moreimages.add(R.drawable.info);
		moreimages.add(R.drawable.rateapp);
		moreimages.add(R.drawable.sharein);
		moreimages.add(R.drawable.contact);
		moreimages.add(R.drawable.feedback87);
		moreimages.add(R.drawable.help);	
		morelistView.setAdapter(new MoreListviewAdapter(this));
		morelistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					Intent insta = new
					Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse("http://twitter.com/search?q=%23instaPunjabi&src=typd&mode=realtime"));
					MoreActivity.this.startActivity(insta);
					break;
							
				case 1:
					 Intent rate=new Intent(MoreActivity.this,Website.class);
					 rate.putExtra("url", "https://play.google.com/store/apps/details?id=com.Insta.instapunjabi");
					 MoreActivity.this. startActivity(rate);
					break;
				case 2:
					Intent share=new Intent(MoreActivity.this,ShareApp.class);
					MoreActivity.this.startActivity(share);
					break;
				case 3:
					Intent contact=new Intent(MoreActivity.this,Synergy.class);
					MoreActivity.this.startActivity(contact);
					break;
				case 4:
					Intent feed=new Intent(MoreActivity.this,Feedback.class);
					MoreActivity.this.startActivity(feed);
					break;
				case 5:
					Intent help=new Intent(MoreActivity.this,Help.class);
					MoreActivity.this.startActivity(help);
					break;
					
				default:
					break;
				}
				
			}
		});

	}//oncreate
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}