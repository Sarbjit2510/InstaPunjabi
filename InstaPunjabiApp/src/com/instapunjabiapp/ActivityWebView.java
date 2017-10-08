/**
 * Copyright 2011 Mark Wyszomierski
 */
package com.instapunjabiapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.analytics.tracking.android.EasyTracker;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * https://developer.foursquare.com/docs/oauth.html
 * https://foursquare.com/oauth/
 * 
 * @date May 17, 2011
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class ActivityWebView extends Activity 
{
    //private static final String TAG = "ActivityWebView";
    
    static String accessToken="";
    String id;
    String path;
    String jsonResult;
    /**
     * Get these values after registering your oauth app at: https://foursquare.com/oauth/
     */
    public static final String OAUTH_CALLBACK_HOST = "callback";
    public static final String CALLBACK_URL = "https://es.foursquare.com"+ "://" + OAUTH_CALLBACK_HOST;
    public static final String CLIENT_ID = "EJRTOGASZJ4WQ1U3AVN1XIHC2ITED54IN0OD1NQZN2IGKIDV";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        path = intent.getStringExtra("imagepath");
        Log.v("webAcitvityyyyyyyy",""+path);
        String url =
            "https://foursquare.com/oauth2/authenticate" + 
                "?client_id=" + CLIENT_ID + 
                "&response_type=token" + 
                "&redirect_uri=" + CALLBACK_URL;
        
        // If authentication works, we'll get redirected to a url with a pattern like:  
        //
        //    http://YOUR_REGISTERED_REDIRECT_URI/#access_token=ACCESS_TOKEN
        //
        // We can override onPageStarted() in the web client and grab the token out.
        WebView webview = (WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String fragment = "#access_token=";
                int start = url.indexOf(fragment);
                if (start > -1) {
                    // You can use the accessToken for api calls now.
                     accessToken = url.substring(start + fragment.length(), url.length());
        			
                   // Log.v(TAG, "OAuth complete, token: [" + accessToken + "].");
              
                    Toast.makeText(ActivityWebView.this, "Token: " + accessToken, Toast.LENGTH_SHORT).show();
                    if(accessToken!=null){
                  AlertDialog.Builder builder = new AlertDialog.Builder(ActivityWebView.this);
                  builder.setMessage("Aunticated user ")
                        .setNeutralButton("ok",new DialogInterface.OnClickListener() {
          		public void onClick(DialogInterface dialog,int id) {
          			// if this button is clicked, just close
          			// the dialog box and do nothing
          			LongOperation ll=new LongOperation();
    				ll.execute();
          		
          		}
          	});
                  AlertDialog alert = builder.create();
                  alert.show();
                    }
                    
                }
            }
        });
        webview.loadUrl(url);

    }

	@Override
public void onStart() {
// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance().activityStart(this);


}

	@Override
public void onStop() {
super.onStop();
	   
EasyTracker.getInstance().activityStop(this); 
	  }

    public class LongOperation extends AsyncTask<Void,Void,Void>{
    	ProgressDialog dialog = new ProgressDialog(ActivityWebView.this);

    		@Override
    	protected void onPostExecute(Void result) {
    		// TODO Auto-generated method stub
    		super.onPostExecute(result);
    		try {
				dialog.dismiss();
				dialog=null;
				//Toast.makeText(ActivityWebView.this, ""+jsonResult, Toast.LENGTH_LONG).show();
  	  Toast.makeText(ActivityWebView.this, "Image uploading to selected place"+id, Toast.LENGTH_LONG).show();
				LongOperation2 ll= new LongOperation2();
				ll.execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    		dialog.setMessage("Loading.....");
    		dialog.show();
    	}

    		@Override
    		protected Void doInBackground(Void... params) {
    			// TODO Auto-generated method stub
    			postData();
    			return null;
    		}
    		
    	}
    	public class LongOperation2 extends AsyncTask<Void,Void,Void>{
    	ProgressDialog dialog = new ProgressDialog(ActivityWebView.this);

    		@Override
    	protected void onPostExecute(Void result) {
    		// TODO Auto-generated method stub
    		super.onPostExecute(result);
    		
    	        	try {
						dialog.dismiss();
						dialog=null;
 		
 		ActivityWebView.this.finish();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	}

    	@Override
    	protected void onPreExecute() {
    		// TODO Auto-generated method stub
    		super.onPreExecute();
    		dialog.setMessage("Uploading image");
    		dialog.show();
    	}

    		@Override
    		protected Void doInBackground(Void... params) {
    			// TODO Auto-generated method stub
    			uploadpic();
    			return null;
    		}
    		
    	}
    	public void uploadpic(){
    		System.out.println("idddddddddddddd and outhhhhhhhhhhhhhhhhhhhhhhhhhhhh"+id+"  outh="+accessToken);
    		
    		Bitmap bitmapOrg = null;
//    		try {
//    			  
//    			  bitmapOrg = BitmapFactory.decodeFile(path);
//    			  
//    			} catch (OutOfMemoryError e) {
//    			  try {
//    				  BitmapFactory.Options	  options = new BitmapFactory.Options();
//    			    options.inSampleSize = 2;
//    			    bitmapOrg = BitmapFactory.decodeFile(imageInSD, options);
//    			   
//    			  } catch(Exception e1) {
//    			    Log.e("errroe",""+ e1);
//    			  }
//    			}
    		 bitmapOrg = BitmapFactory.decodeFile(path);
    		    ByteArrayOutputStream bao = new ByteArrayOutputStream();
    		    bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 90, bao);

    		    byte [] ba = bao.toByteArray();
    		   

    		
    	
    	 

    	   try {
    	       HttpClient httpclient = new DefaultHttpClient();
    	     //HttpPost httppost = new HttpPost("https://api.foursquare.com/v2/photos/add?v=20130726&oauth_token="+ActivityWebView.accessToken);
    	   		ByteArrayBody bab = new ByteArrayBody(ba,path);
    	       HttpPost httppost = new HttpPost("https://api.foursquare.com/v2/photos/add?checkinId="+id+"&oauth_token="+accessToken+"&v=20140714");

    	     MultipartEntity reqEntity = new MultipartEntity(
    	             HttpMultipartMode.BROWSER_COMPATIBLE);
    	     reqEntity.addPart("uploaded", bab);
    	     reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
    	     httppost.setEntity(reqEntity);
    	     HttpResponse response = httpclient.execute(httppost);
    	         jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
    	     System.out.println("Response: " + jsonResult);
    	   


    	 } catch (Exception e) {
    	     // handle exception here
    	     Log.e(e.getClass().getName(), e.getMessage());
    	 }
    	}

    	    public void postData() {
    		    // Create a new HttpClient and Post Headerpublic void postData() {
    	        // Create a new HttpClient and Post Header
    	    	
    	    	
    	    
    	    	
    	    	try {
    	    	
    			    	String linktohit = "https://api.foursquare.com/v2/checkins/add?oauth_token="+ActivityWebView.accessToken+"&v=20140714";
    	    		
    	    		System.out.println("linktohit "+linktohit);
    			        

    			        HttpClient httpclient = new DefaultHttpClient();
    			        HttpPost httppost = new HttpPost(linktohit);
    	            
    	                // Add your data
    	            	System.out.println("CHECKPOINT");
    	                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    	                nameValuePairs.add(new BasicNameValuePair("venueId",CheckIn.venueid));
    	                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    	                // Execute HTTP Post Request
    	                HttpResponse response = httpclient.execute(httppost);
    	                
    	          jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
    	         

    	                try {
    					
    					
    	                    JSONObject JSONresponse=new JSONObject(jsonResult);
    	                    JSONObject reponseJson= JSONresponse.getJSONObject("response");
    					
    	                     id = reponseJson.getJSONObject("checkin").getString("id");

    	               	 
    	                    Log.v("id", "v"+id);
    						


    	                
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
//    						AlertDialog.Builder builder = new AlertDialog.Builder(ActivityWebView.this);
//    				        builder.setMessage("Please select the Place first")
//    				               .setNeutralButton("Close",new DialogInterface.OnClickListener() {
//									
//									@Override
//									public void onClick(DialogInterface dialog, int which) {
//										// TODO Auto-generated method stub
//										dialog.dismiss();
//										ActivityWebView.this.finish();
//									}
//								});
//    				        AlertDialog alert = builder.create();
//    				        alert.show();
    					}

    	            } catch (ClientProtocolException e) {
    	                // TODO Auto-generated catch block
    	            	Toast.makeText(ActivityWebView.this,"Error"+e,Toast.LENGTH_LONG).show();
    	            } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
    	       
    	    } 
    	    private StringBuilder inputStreamToString(InputStream is) {
    	        String rLine = "";
    	       StringBuilder answer = new StringBuilder();
    	       BufferedReader rd = new BufferedReader(new InputStreamReader(is));

    	       try {
    	        while ((rLine = rd.readLine()) != null) {
    	          answer.append(rLine);
    	          }
    	       }

    	       catch (IOException e) {
    	           e.printStackTrace();
    	        }
    	       return answer;
    	      }
}