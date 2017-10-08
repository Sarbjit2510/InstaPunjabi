package com.instapunjabiapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;



public class Website extends Activity {

	WebView we;
	private Button back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_website);
		we=(WebView)findViewById(R.id.website);
		back=(Button)findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		String url=getIntent().getStringExtra("url");
		if(url!=null){
		if(!url.startsWith("http")){
			url="http://"+url;
		}
		we.loadUrl(url);
		}
	}
	}
		