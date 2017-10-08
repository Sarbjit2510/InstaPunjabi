package com.instapunjabiapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Synergy extends Activity{
	private Button back;
	private Button web;
	private Button blog;
	private Button face;
	private Button twit;
	private Button youtub;
	private Button feedback;
	private Button linkedin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.synergy_screen);
		web=(Button)findViewById(R.id.webutton);
		blog=(Button)findViewById(R.id.blog);
		linkedin=(Button)findViewById(R.id.linkedinbutton);
		face=(Button)findViewById(R.id.facebookbutton);
		twit=(Button)findViewById(R.id.twitterbutton);
		youtub=(Button)findViewById(R.id.youtubebutton);
		feedback=(Button)findViewById(R.id.feedb);
		back=(Button)findViewById(R.id.back);
 		back.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				finish();
 				
 			}
 		});
 		web.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				 Intent web=new Intent(Synergy.this,Website.class);
 				web.putExtra("url", "http://www.01s.in");
 	        	 startActivity(web);
 			}
 		});
 		blog.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				 Intent blog=new Intent(Synergy.this,Website.class);
 				blog.putExtra("url", "http://blog.01synergy.com");
	        	 startActivity(blog);
 			}
 		});
		face.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				 Intent face=new Intent(Synergy.this,Website.class);
 				face.putExtra("url", "http://m.facebook.com/pages/01-Synergy/17024280066?sk=app_112177175532845");
	        	 startActivity(face);
 			}
 		});
		twit.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				 Intent twit=new Intent(Synergy.this,Website.class);
 				twit.putExtra("url", "http://twitter.com/#!/01stweets");
	        	 startActivity(twit);
 			}
 		});
		linkedin.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				 Intent twit=new Intent(Synergy.this,Website.class);
 				twit.putExtra("url", "http://www.linkedin.com/company/01-synergy");
	        	 startActivity(twit);
 			}
 		});
		youtub.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				 Intent youtub=new Intent(Synergy.this,Website.class);
 				youtub.putExtra("url", "http://www.youtube.com/user/01schannel");
	        	 startActivity(youtub);
 			}
 		});
		feedback.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				 Email(); 
 			}
 		});
	}
	private void Email() {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		

		emailIntent.setType("text/html");
		emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"apps@01s.in"});
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
		//emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+outputFile));
		//emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(selectedImage.toString()));
		Intent emailChooser = Intent.createChooser(emailIntent, "Send Feedback");
		emailChooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
		startActivity(emailChooser);
		} catch (ActivityNotFoundException e) {
		// If there is nothing that can send a text/html MIME type
		e.printStackTrace();
		}
	}
}
