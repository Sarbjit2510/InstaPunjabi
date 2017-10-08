package com.instapunjabiapp;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import nit.TwitterApp;
import nit.TwitterSession;

import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TwitterUploader {
	 static final String twitpic_api_key = "2c6aca38cc0a5fa881635108e0a4e2b1";
	  static final String twitter_consumer_key = "VFWYcYxFMvJMNmGjxRzIQw";
		 static final String twitter_secret_key = "T2hzVtWV57PmcLk5e5vK54oZ5t6uquz14eQRs1Y";
		Context context;
		ArrayList<String> filenames;
		TwitterApp mTwitter;
		String Path;
		private URL Url;
	public TwitterUploader(Context context,TwitterApp mTwitter) {
		this.context=context;
		this.mTwitter=mTwitter;
	}
	public void setImage(String filename){
		Path=filename;
		new ImageSender().execute(Url);
		
	}
	public void setArrayImages(ArrayList<String> filenames){
		this.filenames=filenames;
		
//		 new ImageArraySender().execute(Url);
	}
   public void setVideo(String filename){
	   Path=filename;
	   new ImageSender().execute(Url);
	  
	   
	}
	public class ImageSender extends AsyncTask<URL, Integer, Long> {
		private String url;
		private ProgressDialog mProgressDialog;
		private Bitmap si;
		
		@Override
		protected void onPreExecute() {
			mProgressDialog = ProgressDialog.show(context, "", "Sending File...", true);
			
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}
		
	    @Override
		protected Long doInBackground(URL... urls) {            
	        long result = 0;
	            
					
				   
	        TwitterSession twitterSession	= new TwitterSession(context);            
	        AccessToken accessToken 		= twitterSession.getAccessToken();
			
			Configuration conf = new ConfigurationBuilder()                 
	        .setOAuthConsumerKey(twitter_consumer_key) 
	        .setOAuthConsumerSecret(twitter_secret_key) 
	        .setOAuthAccessToken(accessToken.getToken()) 
	        .setOAuthAccessTokenSecret(accessToken.getTokenSecret()) 
	        .build(); 
//			(conf, conf.getOAuthConsumerKey (), conf.getOAuthConsumerSecret (),
//	                new AccessToken (conf.getOAuthAccessToken (), conf.getOAuthAccessTokenSecret ()));
			OAuthAuthorization auth = new OAuthAuthorization(conf);
	        
			ImageUpload upload = new ImageUploadFactory(conf).getInstance(auth);

	        
	        Log.d("vjfh", "Start sending image...");
	        
	        try {
	        	
	        	url = upload.upload(new File(Path),ShareImage.ds);
	        	result = 1;
//	        	mTwitter.updateStatus(url+"   " + InstaMainFragment.ds);
	        	Log.d("vjfh", "Image uploaded, Twitpic url is " + url);	        
	        } catch (Exception e) {		   
	        	Log.e("vjfh", "Failed to send image");
	        	
	        	e.printStackTrace();
	        }
	    
	        return result;
	    }

	    @Override
		protected void onProgressUpdate(Integer... progress) {            
	    }

	    @Override
		protected void onPostExecute(Long result) {
	    	mProgressDialog.cancel();
	    	
	    	String text = (result == 1) ? "Image sent successfully.\n Twitpic url is: " + url : "Failed to send image";
	    	
	    	Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	    }
	}
	
}
